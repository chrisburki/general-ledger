package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.FinancialInstrumentTypeService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.service.dto.FinancialInstrumentTypeDTO;
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
 * REST controller for managing FinancialInstrumentType.
 */
@RestController
@RequestMapping("/api")
public class FinancialInstrumentTypeResource {

    private final Logger log = LoggerFactory.getLogger(FinancialInstrumentTypeResource.class);

    private static final String ENTITY_NAME = "financialInstrumentType";

    private final FinancialInstrumentTypeService financialInstrumentTypeService;

    public FinancialInstrumentTypeResource(FinancialInstrumentTypeService financialInstrumentTypeService) {
        this.financialInstrumentTypeService = financialInstrumentTypeService;
    }

    /**
     * POST  /financial-instrument-types : Create a new financialInstrumentType.
     *
     * @param financialInstrumentTypeDTO the financialInstrumentTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new financialInstrumentTypeDTO, or with status 400 (Bad Request) if the financialInstrumentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/financial-instrument-types")
    @Timed
    public ResponseEntity<FinancialInstrumentTypeDTO> createFinancialInstrumentType(@Valid @RequestBody FinancialInstrumentTypeDTO financialInstrumentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save FinancialInstrumentType : {}", financialInstrumentTypeDTO);
        if (financialInstrumentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new financialInstrumentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinancialInstrumentTypeDTO result = financialInstrumentTypeService.save(financialInstrumentTypeDTO);
        return ResponseEntity.created(new URI("/api/financial-instrument-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /financial-instrument-types : Updates an existing financialInstrumentType.
     *
     * @param financialInstrumentTypeDTO the financialInstrumentTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated financialInstrumentTypeDTO,
     * or with status 400 (Bad Request) if the financialInstrumentTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the financialInstrumentTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/financial-instrument-types")
    @Timed
    public ResponseEntity<FinancialInstrumentTypeDTO> updateFinancialInstrumentType(@Valid @RequestBody FinancialInstrumentTypeDTO financialInstrumentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update FinancialInstrumentType : {}", financialInstrumentTypeDTO);
        if (financialInstrumentTypeDTO.getId() == null) {
            return createFinancialInstrumentType(financialInstrumentTypeDTO);
        }
        FinancialInstrumentTypeDTO result = financialInstrumentTypeService.save(financialInstrumentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, financialInstrumentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /financial-instrument-types : get all the financialInstrumentTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of financialInstrumentTypes in body
     */
    @GetMapping("/financial-instrument-types")
    @Timed
    public List<FinancialInstrumentTypeDTO> getAllFinancialInstrumentTypes() {
        log.debug("REST request to get all FinancialInstrumentTypes");
        return financialInstrumentTypeService.findAll();
        }

    /**
     * GET  /financial-instrument-types/:id : get the "id" financialInstrumentType.
     *
     * @param id the id of the financialInstrumentTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the financialInstrumentTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/financial-instrument-types/{id}")
    @Timed
    public ResponseEntity<FinancialInstrumentTypeDTO> getFinancialInstrumentType(@PathVariable Long id) {
        log.debug("REST request to get FinancialInstrumentType : {}", id);
        FinancialInstrumentTypeDTO financialInstrumentTypeDTO = financialInstrumentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(financialInstrumentTypeDTO));
    }

    /**
     * DELETE  /financial-instrument-types/:id : delete the "id" financialInstrumentType.
     *
     * @param id the id of the financialInstrumentTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/financial-instrument-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteFinancialInstrumentType(@PathVariable Long id) {
        log.debug("REST request to delete FinancialInstrumentType : {}", id);
        financialInstrumentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
