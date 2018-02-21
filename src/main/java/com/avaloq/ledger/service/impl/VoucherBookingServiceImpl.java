package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.VoucherBookingService;
import com.avaloq.ledger.domain.VoucherBooking;
import com.avaloq.ledger.repository.VoucherBookingRepository;
import com.avaloq.ledger.service.dto.VoucherBookingDTO;
import com.avaloq.ledger.service.mapper.VoucherBookingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing VoucherBooking.
 */
@Service
@Transactional
public class VoucherBookingServiceImpl implements VoucherBookingService {

    private final Logger log = LoggerFactory.getLogger(VoucherBookingServiceImpl.class);

    private final VoucherBookingRepository voucherBookingRepository;

    private final VoucherBookingMapper voucherBookingMapper;

    public VoucherBookingServiceImpl(VoucherBookingRepository voucherBookingRepository, VoucherBookingMapper voucherBookingMapper) {
        this.voucherBookingRepository = voucherBookingRepository;
        this.voucherBookingMapper = voucherBookingMapper;
    }

    /**
     * Save a voucherBooking.
     *
     * @param voucherBookingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VoucherBookingDTO save(VoucherBookingDTO voucherBookingDTO) {
        log.debug("Request to save VoucherBooking : {}", voucherBookingDTO);
        VoucherBooking voucherBooking = voucherBookingMapper.toEntity(voucherBookingDTO);
        voucherBooking = voucherBookingRepository.save(voucherBooking);
        return voucherBookingMapper.toDto(voucherBooking);
    }

    /**
     * Get all the voucherBookings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VoucherBookingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VoucherBookings");
        return voucherBookingRepository.findAll(pageable)
            .map(voucherBookingMapper::toDto);
    }

    /**
     * Get one voucherBooking by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VoucherBookingDTO findOne(Long id) {
        log.debug("Request to get VoucherBooking : {}", id);
        VoucherBooking voucherBooking = voucherBookingRepository.findOne(id);
        return voucherBookingMapper.toDto(voucherBooking);
    }

    /**
     * Delete the voucherBooking by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoucherBooking : {}", id);
        voucherBookingRepository.delete(id);
    }
}
