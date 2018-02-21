package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.VoucherBooking;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VoucherBooking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherBookingRepository extends JpaRepository<VoucherBooking, Long> {

}
