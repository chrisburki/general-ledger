package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.LedgerAccountService;
import com.avaloq.ledger.domain.LedgerAccount;
import com.avaloq.ledger.repository.LedgerAccountRepository;
import com.avaloq.ledger.service.dto.LedgerAccountDTO;
import com.avaloq.ledger.service.mapper.LedgerAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing LedgerAccount.
 */
@Service
@Transactional
public class LedgerAccountServiceImpl implements LedgerAccountService {

    private final Logger log = LoggerFactory.getLogger(LedgerAccountServiceImpl.class);

    private final LedgerAccountRepository ledgerAccountRepository;

    private final LedgerAccountMapper ledgerAccountMapper;

    public LedgerAccountServiceImpl(LedgerAccountRepository ledgerAccountRepository, LedgerAccountMapper ledgerAccountMapper) {
        this.ledgerAccountRepository = ledgerAccountRepository;
        this.ledgerAccountMapper = ledgerAccountMapper;
    }

    /**
     * Save a ledgerAccount.
     *
     * @param ledgerAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LedgerAccountDTO save(LedgerAccountDTO ledgerAccountDTO) {
        log.debug("Request to save LedgerAccount : {}", ledgerAccountDTO);
        LedgerAccount ledgerAccount = ledgerAccountMapper.toEntity(ledgerAccountDTO);
        ledgerAccount = ledgerAccountRepository.save(ledgerAccount);
        return ledgerAccountMapper.toDto(ledgerAccount);
    }

    /**
     * Get all the ledgerAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LedgerAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LedgerAccounts");
        return ledgerAccountRepository.findAll(pageable)
            .map(ledgerAccountMapper::toDto);
    }

    /**
     * Get one ledgerAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LedgerAccountDTO findOne(Long id) {
        log.debug("Request to get LedgerAccount : {}", id);
        LedgerAccount ledgerAccount = ledgerAccountRepository.findOne(id);
        return ledgerAccountMapper.toDto(ledgerAccount);
    }

    /**
     * Delete the ledgerAccount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LedgerAccount : {}", id);
        ledgerAccountRepository.delete(id);
    }

    /**
     * Get the chartOfAccountsDTO.
     *
     * @param key the id of the entity
     * @return the entity
     */
    @Override
    public LedgerAccount findByKeyAndLegalEntityId(String key, String legalEntityId) {
        Optional<LedgerAccount> ledgerAccountSearch = ledgerAccountRepository.findByKeyAndLegalEntityId(key, legalEntityId);

        LedgerAccount ledgerAccount = new LedgerAccount();
        if (ledgerAccountSearch.isPresent()) {
            ledgerAccount = ledgerAccountSearch.get();
        }

        return ledgerAccount;

    }

}
