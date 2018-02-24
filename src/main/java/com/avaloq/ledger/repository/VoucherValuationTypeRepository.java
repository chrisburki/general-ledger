package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.VoucherValuationType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VoucherValuationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherValuationTypeRepository extends JpaRepository<VoucherValuationType, Long> {

}
