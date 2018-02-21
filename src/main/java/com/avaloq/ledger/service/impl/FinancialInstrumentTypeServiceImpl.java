package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.FinancialInstrumentTypeService;
import com.avaloq.ledger.domain.FinancialInstrumentType;
import com.avaloq.ledger.repository.FinancialInstrumentTypeRepository;
import com.avaloq.ledger.service.dto.FinancialInstrumentTypeDTO;
import com.avaloq.ledger.service.mapper.FinancialInstrumentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FinancialInstrumentType.
 */
@Service
@Transactional
public class FinancialInstrumentTypeServiceImpl implements FinancialInstrumentTypeService {

    private final Logger log = LoggerFactory.getLogger(FinancialInstrumentTypeServiceImpl.class);

    private final FinancialInstrumentTypeRepository financialInstrumentTypeRepository;

    private final FinancialInstrumentTypeMapper financialInstrumentTypeMapper;

    public FinancialInstrumentTypeServiceImpl(FinancialInstrumentTypeRepository financialInstrumentTypeRepository, FinancialInstrumentTypeMapper financialInstrumentTypeMapper) {
        this.financialInstrumentTypeRepository = financialInstrumentTypeRepository;
        this.financialInstrumentTypeMapper = financialInstrumentTypeMapper;
    }

    /**
     * Save a financialInstrumentType.
     *
     * @param financialInstrumentTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FinancialInstrumentTypeDTO save(FinancialInstrumentTypeDTO financialInstrumentTypeDTO) {
        log.debug("Request to save FinancialInstrumentType : {}", financialInstrumentTypeDTO);
        FinancialInstrumentType financialInstrumentType = financialInstrumentTypeMapper.toEntity(financialInstrumentTypeDTO);
        financialInstrumentType = financialInstrumentTypeRepository.save(financialInstrumentType);
        return financialInstrumentTypeMapper.toDto(financialInstrumentType);
    }

    /**
     * Get all the financialInstrumentTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FinancialInstrumentTypeDTO> findAll() {
        log.debug("Request to get all FinancialInstrumentTypes");
        return financialInstrumentTypeRepository.findAll().stream()
            .map(financialInstrumentTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one financialInstrumentType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FinancialInstrumentTypeDTO findOne(Long id) {
        log.debug("Request to get FinancialInstrumentType : {}", id);
        FinancialInstrumentType financialInstrumentType = financialInstrumentTypeRepository.findOne(id);
        return financialInstrumentTypeMapper.toDto(financialInstrumentType);
    }

    /**
     * Delete the financialInstrumentType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinancialInstrumentType : {}", id);
        financialInstrumentTypeRepository.delete(id);
    }
}
