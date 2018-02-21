package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.domain.SubLedgerType;

import com.avaloq.ledger.repository.SubLedgerTypeRepository;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.service.dto.SubLedgerTypeDTO;
import com.avaloq.ledger.service.mapper.SubLedgerTypeMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SubLedgerType.
 */
@RestController
@RequestMapping("/api")
public class SubLedgerTypeResource {

    private final Logger log = LoggerFactory.getLogger(SubLedgerTypeResource.class);

    private static final String ENTITY_NAME = "subLedgerType";

    private final SubLedgerTypeRepository subLedgerTypeRepository;

    private final SubLedgerTypeMapper subLedgerTypeMapper;

    public SubLedgerTypeResource(SubLedgerTypeRepository subLedgerTypeRepository, SubLedgerTypeMapper subLedgerTypeMapper) {
        this.subLedgerTypeRepository = subLedgerTypeRepository;
        this.subLedgerTypeMapper = subLedgerTypeMapper;
    }

    /**
     * POST  /sub-ledger-types : Create a new subLedgerType.
     *
     * @param subLedgerTypeDTO the subLedgerTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subLedgerTypeDTO, or with status 400 (Bad Request) if the subLedgerType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sub-ledger-types")
    @Timed
    public ResponseEntity<SubLedgerTypeDTO> createSubLedgerType(@Valid @RequestBody SubLedgerTypeDTO subLedgerTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SubLedgerType : {}", subLedgerTypeDTO);
        if (subLedgerTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new subLedgerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubLedgerType subLedgerType = subLedgerTypeMapper.toEntity(subLedgerTypeDTO);
        subLedgerType = subLedgerTypeRepository.save(subLedgerType);
        SubLedgerTypeDTO result = subLedgerTypeMapper.toDto(subLedgerType);
        return ResponseEntity.created(new URI("/api/sub-ledger-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sub-ledger-types : Updates an existing subLedgerType.
     *
     * @param subLedgerTypeDTO the subLedgerTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subLedgerTypeDTO,
     * or with status 400 (Bad Request) if the subLedgerTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the subLedgerTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sub-ledger-types")
    @Timed
    public ResponseEntity<SubLedgerTypeDTO> updateSubLedgerType(@Valid @RequestBody SubLedgerTypeDTO subLedgerTypeDTO) throws URISyntaxException {
        log.debug("REST request to update SubLedgerType : {}", subLedgerTypeDTO);
        if (subLedgerTypeDTO.getId() == null) {
            return createSubLedgerType(subLedgerTypeDTO);
        }
        SubLedgerType subLedgerType = subLedgerTypeMapper.toEntity(subLedgerTypeDTO);
        subLedgerType = subLedgerTypeRepository.save(subLedgerType);
        SubLedgerTypeDTO result = subLedgerTypeMapper.toDto(subLedgerType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subLedgerTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sub-ledger-types : get all the subLedgerTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subLedgerTypes in body
     */
    @GetMapping("/sub-ledger-types")
    @Timed
    public List<SubLedgerTypeDTO> getAllSubLedgerTypes() {
        log.debug("REST request to get all SubLedgerTypes");
        List<SubLedgerType> subLedgerTypes = subLedgerTypeRepository.findAll();
        return subLedgerTypeMapper.toDto(subLedgerTypes);
        }

    /**
     * GET  /sub-ledger-types/:id : get the "id" subLedgerType.
     *
     * @param id the id of the subLedgerTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subLedgerTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sub-ledger-types/{id}")
    @Timed
    public ResponseEntity<SubLedgerTypeDTO> getSubLedgerType(@PathVariable Long id) {
        log.debug("REST request to get SubLedgerType : {}", id);
        SubLedgerType subLedgerType = subLedgerTypeRepository.findOne(id);
        SubLedgerTypeDTO subLedgerTypeDTO = subLedgerTypeMapper.toDto(subLedgerType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subLedgerTypeDTO));
    }

    /**
     * DELETE  /sub-ledger-types/:id : delete the "id" subLedgerType.
     *
     * @param id the id of the subLedgerTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sub-ledger-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubLedgerType(@PathVariable Long id) {
        log.debug("REST request to delete SubLedgerType : {}", id);
        subLedgerTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
