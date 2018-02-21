package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.ChartOfAccountsService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.web.rest.util.PaginationUtil;
import com.avaloq.ledger.service.dto.ChartOfAccountsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ChartOfAccounts.
 */
@RestController
@RequestMapping("/api")
public class ChartOfAccountsResource {

    private final Logger log = LoggerFactory.getLogger(ChartOfAccountsResource.class);

    private static final String ENTITY_NAME = "chartOfAccounts";

    private final ChartOfAccountsService chartOfAccountsService;

    public ChartOfAccountsResource(ChartOfAccountsService chartOfAccountsService) {
        this.chartOfAccountsService = chartOfAccountsService;
    }

    /**
     * POST  /chart-of-accounts : Create a new chartOfAccounts.
     *
     * @param chartOfAccountsDTO the chartOfAccountsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartOfAccountsDTO, or with status 400 (Bad Request) if the chartOfAccounts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-of-accounts")
    @Timed
    public ResponseEntity<ChartOfAccountsDTO> createChartOfAccounts(@Valid @RequestBody ChartOfAccountsDTO chartOfAccountsDTO) throws URISyntaxException {
        log.debug("REST request to save ChartOfAccounts : {}", chartOfAccountsDTO);
        if (chartOfAccountsDTO.getId() != null) {
            throw new BadRequestAlertException("A new chartOfAccounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChartOfAccountsDTO result = chartOfAccountsService.save(chartOfAccountsDTO);
        return ResponseEntity.created(new URI("/api/chart-of-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-of-accounts : Updates an existing chartOfAccounts.
     *
     * @param chartOfAccountsDTO the chartOfAccountsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartOfAccountsDTO,
     * or with status 400 (Bad Request) if the chartOfAccountsDTO is not valid,
     * or with status 500 (Internal Server Error) if the chartOfAccountsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-of-accounts")
    @Timed
    public ResponseEntity<ChartOfAccountsDTO> updateChartOfAccounts(@Valid @RequestBody ChartOfAccountsDTO chartOfAccountsDTO) throws URISyntaxException {
        log.debug("REST request to update ChartOfAccounts : {}", chartOfAccountsDTO);
        if (chartOfAccountsDTO.getId() == null) {
            return createChartOfAccounts(chartOfAccountsDTO);
        }
        ChartOfAccountsDTO result = chartOfAccountsService.save(chartOfAccountsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chartOfAccountsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-of-accounts : get all the chartOfAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chartOfAccounts in body
     */
    @GetMapping("/chart-of-accounts")
    @Timed
    public ResponseEntity<List<ChartOfAccountsDTO>> getAllChartOfAccounts(Pageable pageable) {
        log.debug("REST request to get a page of ChartOfAccounts");
        Page<ChartOfAccountsDTO> page = chartOfAccountsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chart-of-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chart-of-accounts/:id : get the "id" chartOfAccounts.
     *
     * @param id the id of the chartOfAccountsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartOfAccountsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/chart-of-accounts/{id}")
    @Timed
    public ResponseEntity<ChartOfAccountsDTO> getChartOfAccounts(@PathVariable Long id) {
        log.debug("REST request to get ChartOfAccounts : {}", id);
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chartOfAccountsDTO));
    }

    /**
     * DELETE  /chart-of-accounts/:id : delete the "id" chartOfAccounts.
     *
     * @param id the id of the chartOfAccountsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-of-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartOfAccounts(@PathVariable Long id) {
        log.debug("REST request to delete ChartOfAccounts : {}", id);
        chartOfAccountsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
