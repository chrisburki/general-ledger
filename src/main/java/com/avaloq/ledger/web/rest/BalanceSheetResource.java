package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.BalanceSheetService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.web.rest.util.PaginationUtil;
import com.avaloq.ledger.service.dto.BalanceSheetDTO;
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
 * REST controller for managing BalanceSheet.
 */
@RestController
@RequestMapping("/api")
public class BalanceSheetResource {

    private final Logger log = LoggerFactory.getLogger(BalanceSheetResource.class);

    private static final String ENTITY_NAME = "balanceSheet";

    private final BalanceSheetService balanceSheetService;

    public BalanceSheetResource(BalanceSheetService balanceSheetService) {
        this.balanceSheetService = balanceSheetService;
    }

    /**
     * POST  /balance-sheets : Create a new balanceSheet.
     *
     * @param balanceSheetDTO the balanceSheetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new balanceSheetDTO, or with status 400 (Bad Request) if the balanceSheet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/balance-sheets")
    @Timed
    public ResponseEntity<BalanceSheetDTO> createBalanceSheet(@Valid @RequestBody BalanceSheetDTO balanceSheetDTO) throws URISyntaxException {
        log.debug("REST request to save BalanceSheet : {}", balanceSheetDTO);
        if (balanceSheetDTO.getId() != null) {
            throw new BadRequestAlertException("A new balanceSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BalanceSheetDTO result = balanceSheetService.save(balanceSheetDTO);
        return ResponseEntity.created(new URI("/api/balance-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /balance-sheets : Updates an existing balanceSheet.
     *
     * @param balanceSheetDTO the balanceSheetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated balanceSheetDTO,
     * or with status 400 (Bad Request) if the balanceSheetDTO is not valid,
     * or with status 500 (Internal Server Error) if the balanceSheetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/balance-sheets")
    @Timed
    public ResponseEntity<BalanceSheetDTO> updateBalanceSheet(@Valid @RequestBody BalanceSheetDTO balanceSheetDTO) throws URISyntaxException {
        log.debug("REST request to update BalanceSheet : {}", balanceSheetDTO);
        if (balanceSheetDTO.getId() == null) {
            return createBalanceSheet(balanceSheetDTO);
        }
        BalanceSheetDTO result = balanceSheetService.save(balanceSheetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, balanceSheetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /balance-sheets : get all the balanceSheets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of balanceSheets in body
     */
    @GetMapping("/balance-sheets")
    @Timed
    public ResponseEntity<List<BalanceSheetDTO>> getAllBalanceSheets(Pageable pageable) {
        log.debug("REST request to get a page of BalanceSheets");
        Page<BalanceSheetDTO> page = balanceSheetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/balance-sheets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /balance-sheets/:id : get the "id" balanceSheet.
     *
     * @param id the id of the balanceSheetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the balanceSheetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/balance-sheets/{id}")
    @Timed
    public ResponseEntity<BalanceSheetDTO> getBalanceSheet(@PathVariable Long id) {
        log.debug("REST request to get BalanceSheet : {}", id);
        BalanceSheetDTO balanceSheetDTO = balanceSheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(balanceSheetDTO));
    }

    /**
     * DELETE  /balance-sheets/:id : delete the "id" balanceSheet.
     *
     * @param id the id of the balanceSheetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/balance-sheets/{id}")
    @Timed
    public ResponseEntity<Void> deleteBalanceSheet(@PathVariable Long id) {
        log.debug("REST request to delete BalanceSheet : {}", id);
        balanceSheetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
