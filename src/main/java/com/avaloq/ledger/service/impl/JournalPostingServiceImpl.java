package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.JournalPostingService;
import com.avaloq.ledger.domain.JournalPosting;
import com.avaloq.ledger.repository.JournalPostingRepository;
import com.avaloq.ledger.service.dto.JournalPostingDTO;
import com.avaloq.ledger.service.mapper.JournalPostingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing JournalPosting.
 */
@Service
@Transactional
public class JournalPostingServiceImpl implements JournalPostingService {

    private final Logger log = LoggerFactory.getLogger(JournalPostingServiceImpl.class);

    private final JournalPostingRepository journalPostingRepository;

    private final JournalPostingMapper journalPostingMapper;

    public JournalPostingServiceImpl(JournalPostingRepository journalPostingRepository, JournalPostingMapper journalPostingMapper) {
        this.journalPostingRepository = journalPostingRepository;
        this.journalPostingMapper = journalPostingMapper;
    }

    /**
     * Save a journalPosting.
     *
     * @param journalPostingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JournalPostingDTO save(JournalPostingDTO journalPostingDTO) {
        log.debug("Request to save JournalPosting : {}", journalPostingDTO);
        JournalPosting journalPosting = journalPostingMapper.toEntity(journalPostingDTO);
        journalPosting = journalPostingRepository.save(journalPosting);
        return journalPostingMapper.toDto(journalPosting);
    }

    /**
     * Get all the journalPostings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JournalPostingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JournalPostings");
        return journalPostingRepository.findAll(pageable)
            .map(journalPostingMapper::toDto);
    }

    /**
     * Get one journalPosting by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JournalPostingDTO findOne(Long id) {
        log.debug("Request to get JournalPosting : {}", id);
        JournalPosting journalPosting = journalPostingRepository.findOne(id);
        return journalPostingMapper.toDto(journalPosting);
    }

    /**
     * Delete the journalPosting by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JournalPosting : {}", id);
        journalPostingRepository.delete(id);
    }
}
