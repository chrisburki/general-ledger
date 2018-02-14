package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.ChartOfAccounts;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ChartOfAccounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChartOfAccountsRepository extends JpaRepository<ChartOfAccounts, Long> {

}
