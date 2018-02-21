package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.VoucherPositionService;
import com.avaloq.ledger.domain.VoucherPosition;
import com.avaloq.ledger.repository.VoucherPositionRepository;
import com.avaloq.ledger.service.dto.VoucherPositionDTO;
import com.avaloq.ledger.service.mapper.VoucherPositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing VoucherPosition.
 */
@Service
@Transactional
public class VoucherPositionServiceImpl implements VoucherPositionService {

    private final Logger log = LoggerFactory.getLogger(VoucherPositionServiceImpl.class);

    private final VoucherPositionRepository voucherPositionRepository;

    private final VoucherPositionMapper voucherPositionMapper;

    public VoucherPositionServiceImpl(VoucherPositionRepository voucherPositionRepository, VoucherPositionMapper voucherPositionMapper) {
        this.voucherPositionRepository = voucherPositionRepository;
        this.voucherPositionMapper = voucherPositionMapper;
    }

    /**
     * Save a voucherPosition.
     *
     * @param voucherPositionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VoucherPositionDTO save(VoucherPositionDTO voucherPositionDTO) {
        log.debug("Request to save VoucherPosition : {}", voucherPositionDTO);
        VoucherPosition voucherPosition = voucherPositionMapper.toEntity(voucherPositionDTO);
        voucherPosition = voucherPositionRepository.save(voucherPosition);
        return voucherPositionMapper.toDto(voucherPosition);
    }

    /**
     * Get all the voucherPositions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VoucherPositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VoucherPositions");
        return voucherPositionRepository.findAll(pageable)
            .map(voucherPositionMapper::toDto);
    }

    /**
     * Get one voucherPosition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VoucherPositionDTO findOne(Long id) {
        log.debug("Request to get VoucherPosition : {}", id);
        VoucherPosition voucherPosition = voucherPositionRepository.findOne(id);
        return voucherPositionMapper.toDto(voucherPosition);
    }

    /**
     * Delete the voucherPosition by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoucherPosition : {}", id);
        voucherPositionRepository.delete(id);
    }
}
