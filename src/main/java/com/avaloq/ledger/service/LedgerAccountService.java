package com.avaloq.ledger.service;

import com.avaloq.ledger.service.dto.LedgerAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LedgerAccount.
 */
public interface LedgerAccountService {

    /**
     * Save a ledgerAccount.
     *
     * @param ledgerAccountDTO the entity to save
     * @return the persisted entity
     */
    LedgerAccountDTO save(LedgerAccountDTO ledgerAccountDTO);

    /**
     * Get all the ledgerAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LedgerAccountDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ledgerAccount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LedgerAccountDTO findOne(Long id);

    /**
     * Delete the "id" ledgerAccount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
