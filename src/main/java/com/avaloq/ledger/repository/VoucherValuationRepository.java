package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.VoucherValuation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VoucherValuation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherValuationRepository extends JpaRepository<VoucherValuation, Long> {

}
