package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.ChartOfAccountsService;
import com.avaloq.ledger.domain.ChartOfAccounts;
import com.avaloq.ledger.repository.ChartOfAccountsRepository;
import com.avaloq.ledger.service.dto.ChartOfAccountsDTO;
import com.avaloq.ledger.service.mapper.ChartOfAccountsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ChartOfAccounts.
 */
@Service
@Transactional
public class ChartOfAccountsServiceImpl implements ChartOfAccountsService {

    private final Logger log = LoggerFactory.getLogger(ChartOfAccountsServiceImpl.class);

    private final ChartOfAccountsRepository chartOfAccountsRepository;

    private final ChartOfAccountsMapper chartOfAccountsMapper;

    public ChartOfAccountsServiceImpl(ChartOfAccountsRepository chartOfAccountsRepository, ChartOfAccountsMapper chartOfAccountsMapper) {
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.chartOfAccountsMapper = chartOfAccountsMapper;
    }

    /**
     * Save a chartOfAccounts.
     *
     * @param chartOfAccountsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChartOfAccountsDTO save(ChartOfAccountsDTO chartOfAccountsDTO) {
        log.debug("Request to save ChartOfAccounts : {}", chartOfAccountsDTO);
        ChartOfAccounts chartOfAccounts = chartOfAccountsMapper.toEntity(chartOfAccountsDTO);
        chartOfAccounts = chartOfAccountsRepository.save(chartOfAccounts);
        return chartOfAccountsMapper.toDto(chartOfAccounts);
    }

    /**
     * Get all the chartOfAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChartOfAccountsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChartOfAccounts");
        return chartOfAccountsRepository.findAll(pageable)
            .map(chartOfAccountsMapper::toDto);
    }

    /**
     * Get one chartOfAccounts by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ChartOfAccountsDTO findOne(Long id) {
        log.debug("Request to get ChartOfAccounts : {}", id);
        ChartOfAccounts chartOfAccounts = chartOfAccountsRepository.findOne(id);
        return chartOfAccountsMapper.toDto(chartOfAccounts);
    }

    /**
     * Delete the chartOfAccounts by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChartOfAccounts : {}", id);
        chartOfAccountsRepository.delete(id);
    }
}
