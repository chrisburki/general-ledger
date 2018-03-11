package com.avaloq.ledger.service;

import com.avaloq.ledger.domain.VoucherBooking;
import com.avaloq.ledger.domain.enumeration.BalanceDateType;
import com.avaloq.ledger.service.dto.VoucherBookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * Find all voucherBooking with date and date type.
     *
     * @param dateFrom from-date
     * @param dateTo to-date
     * @param dateType date type
     */
    List<VoucherBooking> findAllByDateAndDateType(LocalDate dateFrom, LocalDate dateTo, BalanceDateType dateType);
}
