package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.LedgerAccount;
import com.avaloq.ledger.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the LedgerAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LedgerAccountRepository extends JpaRepository<LedgerAccount, Long> {

    Optional<LedgerAccount> findByKeyAndLegalEntityId(String key, String legalEntityId);

}
