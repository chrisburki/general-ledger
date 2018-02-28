package com.avaloq.ledger.service.impl;

import com.avaloq.ledger.service.BalanceSheetItemService;
import com.avaloq.ledger.domain.BalanceSheetItem;
import com.avaloq.ledger.repository.BalanceSheetItemRepository;
import com.avaloq.ledger.service.dto.BalanceSheetItemDTO;
import com.avaloq.ledger.service.mapper.BalanceSheetItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BalanceSheetItem.
 */
@Service
@Transactional
public class BalanceSheetItemServiceImpl implements BalanceSheetItemService {

    private final Logger log = LoggerFactory.getLogger(BalanceSheetItemServiceImpl.class);

    private final BalanceSheetItemRepository balanceSheetItemRepository;

    private final BalanceSheetItemMapper balanceSheetItemMapper;

    public BalanceSheetItemServiceImpl(BalanceSheetItemRepository balanceSheetItemRepository, BalanceSheetItemMapper balanceSheetItemMapper) {
        this.balanceSheetItemRepository = balanceSheetItemRepository;
        this.balanceSheetItemMapper = balanceSheetItemMapper;
    }

    /**
     * Save a balanceSheetItem.
     *
     * @param balanceSheetItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BalanceSheetItemDTO save(BalanceSheetItemDTO balanceSheetItemDTO) {
        log.debug("Request to save BalanceSheetItem : {}", balanceSheetItemDTO);
        BalanceSheetItem balanceSheetItem = balanceSheetItemMapper.toEntity(balanceSheetItemDTO);
        balanceSheetItem = balanceSheetItemRepository.save(balanceSheetItem);
        return balanceSheetItemMapper.toDto(balanceSheetItem);
    }

    /**
     * Get all the balanceSheetItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BalanceSheetItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BalanceSheetItems");
        return balanceSheetItemRepository.findAll(pageable)
            .map(balanceSheetItemMapper::toDto);
    }

    /**
     * Get one balanceSheetItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BalanceSheetItemDTO findOne(Long id) {
        log.debug("Request to get BalanceSheetItem : {}", id);
        BalanceSheetItem balanceSheetItem = balanceSheetItemRepository.findOne(id);
        return balanceSheetItemMapper.toDto(balanceSheetItem);
    }

    /**
     * Delete the balanceSheetItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BalanceSheetItem : {}", id);
        balanceSheetItemRepository.delete(id);
    }
}
