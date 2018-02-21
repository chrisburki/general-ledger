package com.avaloq.ledger.service;

import com.avaloq.ledger.service.dto.ChartOfAccountsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ChartOfAccounts.
 */
public interface ChartOfAccountsService {

    /**
     * Save a chartOfAccounts.
     *
     * @param chartOfAccountsDTO the entity to save
     * @return the persisted entity
     */
    ChartOfAccountsDTO save(ChartOfAccountsDTO chartOfAccountsDTO);

    /**
     * Get all the chartOfAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ChartOfAccountsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" chartOfAccounts.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ChartOfAccountsDTO findOne(Long id);

    /**
     * Delete the "id" chartOfAccounts.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
