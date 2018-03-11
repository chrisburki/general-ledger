package com.avaloq.ledger.repository;

import com.avaloq.ledger.domain.FinancialInstrumentType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the FinancialInstrumentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinancialInstrumentTypeRepository extends JpaRepository<FinancialInstrumentType, Long> {

    Optional<FinancialInstrumentType> findByCategory(String category);

}
