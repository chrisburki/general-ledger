package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.BalanceSheetItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BalanceSheetItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceSheetItemRepository extends JpaRepository<BalanceSheetItem, Long> {

}
