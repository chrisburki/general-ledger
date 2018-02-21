package com.avaloq.ledger.service;

import com.avaloq.ledger.service.dto.FinancialInstrumentTypeDTO;
import java.util.List;

/**
 * Service Interface for managing FinancialInstrumentType.
 */
public interface FinancialInstrumentTypeService {

    /**
     * Save a financialInstrumentType.
     *
     * @param financialInstrumentTypeDTO the entity to save
     * @return the persisted entity
     */
    FinancialInstrumentTypeDTO save(FinancialInstrumentTypeDTO financialInstrumentTypeDTO);

    /**
     * Get all the financialInstrumentTypes.
     *
     * @return the list of entities
     */
    List<FinancialInstrumentTypeDTO> findAll();

    /**
     * Get the "id" financialInstrumentType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FinancialInstrumentTypeDTO findOne(Long id);

    /**
     * Delete the "id" financialInstrumentType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
