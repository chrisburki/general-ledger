package com.avaloq.ledger.service;

import com.avaloq.ledger.service.dto.VoucherBookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VoucherBooking.
 */
public interface VoucherBookingService {

    /**
     * Save a voucherBooking.
     *
     * @param voucherBookingDTO the entity to save
     * @return the persisted entity
     */
    VoucherBookingDTO save(VoucherBookingDTO voucherBookingDTO);

    /**
     * Get all the voucherBookings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VoucherBookingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" voucherBooking.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VoucherBookingDTO findOne(Long id);

    /**
     * Delete the "id" voucherBooking.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
