package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.VoucherValuationService;
import com.avaloq.ledger.domain.VoucherValuation;
import com.avaloq.ledger.repository.VoucherValuationRepository;
import com.avaloq.ledger.service.dto.VoucherValuationDTO;
import com.avaloq.ledger.service.mapper.VoucherValuationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing VoucherValuation.
 */
@Service
@Transactional
public class VoucherValuationServiceImpl implements VoucherValuationService {

    private final Logger log = LoggerFactory.getLogger(VoucherValuationServiceImpl.class);

    private final VoucherValuationRepository voucherValuationRepository;

    private final VoucherValuationMapper voucherValuationMapper;

    public VoucherValuationServiceImpl(VoucherValuationRepository voucherValuationRepository, VoucherValuationMapper voucherValuationMapper) {
        this.voucherValuationRepository = voucherValuationRepository;
        this.voucherValuationMapper = voucherValuationMapper;
    }

    /**
     * Save a voucherValuation.
     *
     * @param voucherValuationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VoucherValuationDTO save(VoucherValuationDTO voucherValuationDTO) {
        log.debug("Request to save VoucherValuation : {}", voucherValuationDTO);
        VoucherValuation voucherValuation = voucherValuationMapper.toEntity(voucherValuationDTO);
        voucherValuation = voucherValuationRepository.save(voucherValuation);
        return voucherValuationMapper.toDto(voucherValuation);
    }

    /**
     * Get all the voucherValuations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VoucherValuationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VoucherValuations");
        return voucherValuationRepository.findAll(pageable)
            .map(voucherValuationMapper::toDto);
    }

    /**
     * Get one voucherValuation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VoucherValuationDTO findOne(Long id) {
        log.debug("Request to get VoucherValuation : {}", id);
        VoucherValuation voucherValuation = voucherValuationRepository.findOne(id);
        return voucherValuationMapper.toDto(voucherValuation);
    }

    /**
     * Delete the voucherValuation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoucherValuation : {}", id);
        voucherValuationRepository.delete(id);
    }
}
