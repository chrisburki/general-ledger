package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.LedgerAccountService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.web.rest.util.PaginationUtil;
import com.avaloq.ledger.service.dto.LedgerAccountDTO;
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
 * REST controller for managing LedgerAccount.
 */
@RestController
@RequestMapping("/api")
public class LedgerAccountResource {

    private final Logger log = LoggerFactory.getLogger(LedgerAccountResource.class);

    private static final String ENTITY_NAME = "ledgerAccount";

    private final LedgerAccountService ledgerAccountService;

    public LedgerAccountResource(LedgerAccountService ledgerAccountService) {
        this.ledgerAccountService = ledgerAccountService;
    }

    /**
     * POST  /ledger-accounts : Create a new ledgerAccount.
     *
     * @param ledgerAccountDTO the ledgerAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ledgerAccountDTO, or with status 400 (Bad Request) if the ledgerAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ledger-accounts")
    @Timed
    public ResponseEntity<LedgerAccountDTO> createLedgerAccount(@Valid @RequestBody LedgerAccountDTO ledgerAccountDTO) throws URISyntaxException {
        log.debug("REST request to save LedgerAccount : {}", ledgerAccountDTO);
        if (ledgerAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new ledgerAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LedgerAccountDTO result = ledgerAccountService.save(ledgerAccountDTO);
        return ResponseEntity.created(new URI("/api/ledger-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ledger-accounts : Updates an existing ledgerAccount.
     *
     * @param ledgerAccountDTO the ledgerAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ledgerAccountDTO,
     * or with status 400 (Bad Request) if the ledgerAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the ledgerAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ledger-accounts")
    @Timed
    public ResponseEntity<LedgerAccountDTO> updateLedgerAccount(@Valid @RequestBody LedgerAccountDTO ledgerAccountDTO) throws URISyntaxException {
        log.debug("REST request to update LedgerAccount : {}", ledgerAccountDTO);
        if (ledgerAccountDTO.getId() == null) {
            return createLedgerAccount(ledgerAccountDTO);
        }
        LedgerAccountDTO result = ledgerAccountService.save(ledgerAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ledgerAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ledger-accounts : get all the ledgerAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ledgerAccounts in body
     */
    @GetMapping("/ledger-accounts")
    @Timed
    public ResponseEntity<List<LedgerAccountDTO>> getAllLedgerAccounts(Pageable pageable) {
        log.debug("REST request to get a page of LedgerAccounts");
        Page<LedgerAccountDTO> page = ledgerAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ledger-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ledger-accounts/:id : get the "id" ledgerAccount.
     *
     * @param id the id of the ledgerAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ledgerAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ledger-accounts/{id}")
    @Timed
    public ResponseEntity<LedgerAccountDTO> getLedgerAccount(@PathVariable Long id) {
        log.debug("REST request to get LedgerAccount : {}", id);
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ledgerAccountDTO));
    }

    /**
     * DELETE  /ledger-accounts/:id : delete the "id" ledgerAccount.
     *
     * @param id the id of the ledgerAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ledger-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteLedgerAccount(@PathVariable Long id) {
        log.debug("REST request to delete LedgerAccount : {}", id);
        ledgerAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
