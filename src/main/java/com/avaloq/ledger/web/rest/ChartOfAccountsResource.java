package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.domain.ChartOfAccounts;

import com.avaloq.ledger.repository.ChartOfAccountsRepository;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
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
 * REST controller for managing ChartOfAccounts.
 */
@RestController
@RequestMapping("/api")
public class ChartOfAccountsResource {

    private final Logger log = LoggerFactory.getLogger(ChartOfAccountsResource.class);

    private static final String ENTITY_NAME = "chartOfAccounts";

    private final ChartOfAccountsRepository chartOfAccountsRepository;

    public ChartOfAccountsResource(ChartOfAccountsRepository chartOfAccountsRepository) {
        this.chartOfAccountsRepository = chartOfAccountsRepository;
    }

    /**
     * POST  /chart-of-accounts : Create a new chartOfAccounts.
     *
     * @param chartOfAccounts the chartOfAccounts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartOfAccounts, or with status 400 (Bad Request) if the chartOfAccounts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-of-accounts")
    @Timed
    public ResponseEntity<ChartOfAccounts> createChartOfAccounts(@Valid @RequestBody ChartOfAccounts chartOfAccounts) throws URISyntaxException {
        log.debug("REST request to save ChartOfAccounts : {}", chartOfAccounts);
        if (chartOfAccounts.getId() != null) {
            throw new BadRequestAlertException("A new chartOfAccounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChartOfAccounts result = chartOfAccountsRepository.save(chartOfAccounts);
        return ResponseEntity.created(new URI("/api/chart-of-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-of-accounts : Updates an existing chartOfAccounts.
     *
     * @param chartOfAccounts the chartOfAccounts to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartOfAccounts,
     * or with status 400 (Bad Request) if the chartOfAccounts is not valid,
     * or with status 500 (Internal Server Error) if the chartOfAccounts couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-of-accounts")
    @Timed
    public ResponseEntity<ChartOfAccounts> updateChartOfAccounts(@Valid @RequestBody ChartOfAccounts chartOfAccounts) throws URISyntaxException {
        log.debug("REST request to update ChartOfAccounts : {}", chartOfAccounts);
        if (chartOfAccounts.getId() == null) {
            return createChartOfAccounts(chartOfAccounts);
        }
        ChartOfAccounts result = chartOfAccountsRepository.save(chartOfAccounts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chartOfAccounts.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-of-accounts : get all the chartOfAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chartOfAccounts in body
     */
    @GetMapping("/chart-of-accounts")
    @Timed
    public List<ChartOfAccounts> getAllChartOfAccounts() {
        log.debug("REST request to get all ChartOfAccounts");
        return chartOfAccountsRepository.findAll();
        }

    /**
     * GET  /chart-of-accounts/:id : get the "id" chartOfAccounts.
     *
     * @param id the id of the chartOfAccounts to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartOfAccounts, or with status 404 (Not Found)
     */
    @GetMapping("/chart-of-accounts/{id}")
    @Timed
    public ResponseEntity<ChartOfAccounts> getChartOfAccounts(@PathVariable Long id) {
        log.debug("REST request to get ChartOfAccounts : {}", id);
        ChartOfAccounts chartOfAccounts = chartOfAccountsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chartOfAccounts));
    }

    /**
     * DELETE  /chart-of-accounts/:id : delete the "id" chartOfAccounts.
     *
     * @param id the id of the chartOfAccounts to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-of-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartOfAccounts(@PathVariable Long id) {
        log.debug("REST request to delete ChartOfAccounts : {}", id);
        chartOfAccountsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
