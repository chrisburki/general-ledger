package com.avaloq.ledger.service;

import com.avaloq.ledger.service.dto.VoucherPositionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VoucherPosition.
 */
public interface VoucherPositionService {

    /**
     * Save a voucherPosition.
     *
     * @param voucherPositionDTO the entity to save
     * @return the persisted entity
     */
    VoucherPositionDTO save(VoucherPositionDTO voucherPositionDTO);

    /**
     * Get all the voucherPositions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VoucherPositionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" voucherPosition.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VoucherPositionDTO findOne(Long id);

    /**
     * Delete the "id" voucherPosition.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
