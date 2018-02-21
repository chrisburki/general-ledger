package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.BalanceSheetService;
import com.avaloq.ledger.domain.BalanceSheet;
import com.avaloq.ledger.repository.BalanceSheetRepository;
import com.avaloq.ledger.service.dto.BalanceSheetDTO;
import com.avaloq.ledger.service.mapper.BalanceSheetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BalanceSheet.
 */
@Service
@Transactional
public class BalanceSheetServiceImpl implements BalanceSheetService {

    private final Logger log = LoggerFactory.getLogger(BalanceSheetServiceImpl.class);

    private final BalanceSheetRepository balanceSheetRepository;

    private final BalanceSheetMapper balanceSheetMapper;

    public BalanceSheetServiceImpl(BalanceSheetRepository balanceSheetRepository, BalanceSheetMapper balanceSheetMapper) {
        this.balanceSheetRepository = balanceSheetRepository;
        this.balanceSheetMapper = balanceSheetMapper;
    }

    /**
     * Save a balanceSheet.
     *
     * @param balanceSheetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BalanceSheetDTO save(BalanceSheetDTO balanceSheetDTO) {
        log.debug("Request to save BalanceSheet : {}", balanceSheetDTO);
        BalanceSheet balanceSheet = balanceSheetMapper.toEntity(balanceSheetDTO);
        balanceSheet = balanceSheetRepository.save(balanceSheet);
        return balanceSheetMapper.toDto(balanceSheet);
    }

    /**
     * Get all the balanceSheets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BalanceSheetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BalanceSheets");
        return balanceSheetRepository.findAll(pageable)
            .map(balanceSheetMapper::toDto);
    }

    /**
     * Get one balanceSheet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BalanceSheetDTO findOne(Long id) {
        log.debug("Request to get BalanceSheet : {}", id);
        BalanceSheet balanceSheet = balanceSheetRepository.findOne(id);
        return balanceSheetMapper.toDto(balanceSheet);
    }

    /**
     * Delete the balanceSheet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BalanceSheet : {}", id);
        balanceSheetRepository.delete(id);
    }
}
