package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.VoucherPosition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VoucherPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherPositionRepository extends JpaRepository<VoucherPosition, Long> {

}
