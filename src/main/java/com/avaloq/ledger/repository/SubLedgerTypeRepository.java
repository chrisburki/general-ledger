package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.SubLedgerType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SubLedgerType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubLedgerTypeRepository extends JpaRepository<SubLedgerType, Long> {

}
