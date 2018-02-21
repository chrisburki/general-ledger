package com.avaloq.ledger.service;

import com.avaloq.ledger.service.dto.VoucherValuationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VoucherValuation.
 */
public interface VoucherValuationService {

    /**
     * Save a voucherValuation.
     *
     * @param voucherValuationDTO the entity to save
     * @return the persisted entity
     */
    VoucherValuationDTO save(VoucherValuationDTO voucherValuationDTO);

    /**
     * Get all the voucherValuations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VoucherValuationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" voucherValuation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VoucherValuationDTO findOne(Long id);

    /**
     * Delete the "id" voucherValuation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
