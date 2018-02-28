package com.avaloq.ledger.service;

import com.avaloq.ledger.service.dto.BalanceSheetItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BalanceSheetItem.
 */
public interface BalanceSheetItemService {

    /**
     * Save a balanceSheetItem.
     *
     * @param balanceSheetItemDTO the entity to save
     * @return the persisted entity
     */
    BalanceSheetItemDTO save(BalanceSheetItemDTO balanceSheetItemDTO);

    /**
     * Get all the balanceSheetItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BalanceSheetItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" balanceSheetItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BalanceSheetItemDTO findOne(Long id);

    /**
     * Delete the "id" balanceSheetItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
