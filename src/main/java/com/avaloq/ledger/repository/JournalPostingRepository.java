package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.JournalPosting;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JournalPosting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JournalPostingRepository extends JpaRepository<JournalPosting, Long> {

}
