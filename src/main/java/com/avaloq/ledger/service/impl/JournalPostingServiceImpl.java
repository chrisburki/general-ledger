package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.domain.BalanceSheet;
import com.avaloq.ledger.domain.LedgerAccount;
import com.avaloq.ledger.domain.VoucherBooking;
import com.avaloq.ledger.domain.enumeration.BalanceDateType;
import com.avaloq.ledger.repository.BalanceSheetItemRepository;
import com.avaloq.ledger.repository.BalanceSheetRepository;
import com.avaloq.ledger.repository.VoucherBookingRepository;
import com.avaloq.ledger.service.JournalPostingService;
import com.avaloq.ledger.domain.JournalPosting;
import com.avaloq.ledger.repository.JournalPostingRepository;
import com.avaloq.ledger.service.LedgerAccountService;
import com.avaloq.ledger.service.VoucherBookingService;
import com.avaloq.ledger.service.dto.ChartOfAccountsDTO;
import com.avaloq.ledger.service.dto.JournalPostingDTO;
import com.avaloq.ledger.service.mapper.ChartOfAccountsMapper;
import com.avaloq.ledger.service.mapper.JournalPostingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing JournalPosting.
 */
@Service
@Transactional
public class JournalPostingServiceImpl implements JournalPostingService {


    private final Logger log = LoggerFactory.getLogger(JournalPostingServiceImpl.class);

    private final JournalPostingRepository journalPostingRepository;

    private final JournalPostingMapper journalPostingMapper;

    private final BalanceSheetRepository balanceSheetRepository;

    private final BalanceSheetItemRepository balanceSheetItemRepository;

    private final VoucherBookingService voucherBookingService;

    private final LedgerAccountService ledgerAccountService;

    private final ChartOfAccountsMapper chartOfAccountsMapper;

    public JournalPostingServiceImpl(
        JournalPostingRepository journalPostingRepository,
        JournalPostingMapper journalPostingMapper,
        BalanceSheetRepository balanceSheetRepository,
        ChartOfAccountsMapper chartOfAccountsMapper,
        BalanceSheetItemRepository balanceSheetItemRepository,
        VoucherBookingService voucherBookingService,
        LedgerAccountService ledgerAccountService
    ) {
        this.journalPostingRepository = journalPostingRepository;
        this.journalPostingMapper = journalPostingMapper;
        this.balanceSheetRepository = balanceSheetRepository;
        this.chartOfAccountsMapper = chartOfAccountsMapper;
        this.balanceSheetItemRepository = balanceSheetItemRepository;
        this.voucherBookingService = voucherBookingService;
        this.ledgerAccountService = ledgerAccountService;
    }

    /**
     * Save a journalPosting.
     *
     * @param journalPostingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JournalPostingDTO save(JournalPostingDTO journalPostingDTO) {
        log.debug("Request to save JournalPosting : {}", journalPostingDTO);
        JournalPosting journalPosting = journalPostingMapper.toEntity(journalPostingDTO);
        journalPosting = journalPostingRepository.save(journalPosting);
        return journalPostingMapper.toDto(journalPosting);
    }

    /**
     * Get all the journalPostings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JournalPostingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JournalPostings");
        return journalPostingRepository.findAll(pageable)
            .map(journalPostingMapper::toDto);
    }

    /**
     * Get one journalPosting by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JournalPostingDTO findOne(Long id) {
        log.debug("Request to get JournalPosting : {}", id);
        JournalPosting journalPosting = journalPostingRepository.findOne(id);
        return journalPostingMapper.toDto(journalPosting);
    }

    /**
     * Delete the journalPosting by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JournalPosting : {}", id);
        journalPostingRepository.delete(id);
    }

    /**
     * Generate Journal Postings from Voucher.
     *
     * @param refDate            the date per when to create a new balance sheet
     * @param chartOfAccountsDTO DTO of the chart of account
     * @return number of created entity
     */
    @Override
    public Long generateFromVoucher(LocalDate refDate, ChartOfAccountsDTO chartOfAccountsDTO, BalanceDateType dateType, String legalEntityId) {

        // 1. check for existing balance-sheet
        Optional<BalanceSheet> balanceSheetSearch = balanceSheetRepository.findByChartOfAccountsIdAndBalanceDateAndBalanceDateType(chartOfAccountsDTO.getId(), refDate, dateType);
        BalanceSheet balanceSheet = new BalanceSheet();
        Long status;

        // 1a. if exists delete balance-sheet
        if (balanceSheetSearch.isPresent()) {
            // delete balance sheet and its entries;
            balanceSheet = balanceSheetSearch.get();
            balanceSheetItemRepository.deleteByBalanceSheetId(balanceSheet.getId());
            status = 1L;

        // 1b. create new balance-sheet
        } else {
            balanceSheet.setChartOfAccounts(chartOfAccountsMapper.toEntity(chartOfAccountsDTO));
            balanceSheet.setKey(chartOfAccountsDTO.getKey());
            balanceSheet.setBalanceDate(refDate);
            balanceSheet.setBalanceDateType(dateType);
            balanceSheet.setGlobalSequenceNumber(1L);
            balanceSheet.setLegalEntityId(chartOfAccountsDTO.getLegalEntityId());
            balanceSheetRepository.save(balanceSheet);
            status = 2L;
        }

        // 2. select voucher bookings per date
        List<VoucherBooking> voucherBookings = voucherBookingService.findAllByDateAndDateType(refDate, refDate, dateType);

        // 3. loop over voucher bookings, apply posting rule & generate journal postings
        for (VoucherBooking voucherBooking : voucherBookings) {
            JournalPosting journalPosting = new JournalPosting();

            // Get Ledger Account from Rule
            LedgerAccount debitLedgerAccount = ledgerAccountService.findByKeyAndLegalEntityId("2.03.02.01",legalEntityId);
            LedgerAccount creditLedgerAccount = ledgerAccountService.findByKeyAndLegalEntityId("5.06.02.02.01",legalEntityId);

            journalPosting.setDebitAccount( debitLedgerAccount );
            journalPosting.setCreditAccount( creditLedgerAccount );
            journalPosting.setBookDate( voucherBooking.getDate(dateType) );
            journalPosting.setDocumentNumber( voucherBooking.getEventId()+":"+voucherBooking.getGlobalSequenceNumber() );
            journalPosting.setAmount( voucherBooking.getAmountBaseCurrency() );
            journalPosting.setCurrencyIso( voucherBooking.getCurrencyIso() );
            journalPosting.setAmountCurrency( voucherBooking.getAmount() );
            journalPosting.setBookingText( voucherBooking.getBookingText() );
            journalPosting.setGlobalSequenceNumber( voucherBooking.getGlobalSequenceNumber() );
            journalPosting.setLegalEntityId( voucherBooking.getLegalEntityId() );
            journalPostingRepository.save(journalPosting);
        }

        // 4. select voucher valuation per date
        // 5. apply posting rule & generate journal postings

        return status;
    }
}
