package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.BalanceSheet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.Optional;


/**
 * Spring Data JPA repository for the BalanceSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheet, Long> {

    Optional<BalanceSheet> findByChartOfAccountsIdAndBalanceDate(Long chartOfAccountsId, LocalDate date);

}
