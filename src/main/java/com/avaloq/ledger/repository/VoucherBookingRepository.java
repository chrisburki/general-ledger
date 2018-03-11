package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.VoucherBooking;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the VoucherBooking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherBookingRepository extends JpaRepository<VoucherBooking, Long> {

    List<VoucherBooking> findAllByBookDateBetween(LocalDate fromDate, LocalDate toDate);

    List<VoucherBooking> findAllByDoneDateBetween(LocalDate fromDate, LocalDate toDate);
}
