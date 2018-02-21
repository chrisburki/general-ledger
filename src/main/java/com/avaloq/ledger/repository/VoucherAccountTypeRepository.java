package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.VoucherAccountType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VoucherAccountType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherAccountTypeRepository extends JpaRepository<VoucherAccountType, Long> {

}
