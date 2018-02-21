package com.avaloq.ledger.service;

import com.avaloq.ledger.service.dto.BalanceSheetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BalanceSheet.
 */
public interface BalanceSheetService {

    /**
     * Save a balanceSheet.
     *
     * @param balanceSheetDTO the entity to save
     * @return the persisted entity
     */
    BalanceSheetDTO save(BalanceSheetDTO balanceSheetDTO);

    /**
     * Get all the balanceSheets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BalanceSheetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" balanceSheet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BalanceSheetDTO findOne(Long id);

    /**
     * Delete the "id" balanceSheet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
