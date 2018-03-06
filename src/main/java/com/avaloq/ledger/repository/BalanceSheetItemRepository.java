package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.BalanceSheetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the BalanceSheetItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceSheetItemRepository extends JpaRepository<BalanceSheetItem, Long> {

    @Modifying
    @Transactional
    void deleteByBalanceSheetId(Long balanceSheetId);

}
