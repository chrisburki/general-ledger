package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.BalanceSheet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BalanceSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheet, Long> {

}
