package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.BalanceSheetItemService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.web.rest.util.PaginationUtil;
import com.avaloq.ledger.service.dto.BalanceSheetItemDTO;
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
 * REST controller for managing BalanceSheetItem.
 */
@RestController
@RequestMapping("/api")
public class BalanceSheetItemResource {

    private final Logger log = LoggerFactory.getLogger(BalanceSheetItemResource.class);

    private static final String ENTITY_NAME = "balanceSheetItem";

    private final BalanceSheetItemService balanceSheetItemService;

    public BalanceSheetItemResource(BalanceSheetItemService balanceSheetItemService) {
        this.balanceSheetItemService = balanceSheetItemService;
    }

    /**
     * POST  /balance-sheet-items : Create a new balanceSheetItem.
     *
     * @param balanceSheetItemDTO the balanceSheetItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new balanceSheetItemDTO, or with status 400 (Bad Request) if the balanceSheetItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/balance-sheet-items")
    @Timed
    public ResponseEntity<BalanceSheetItemDTO> createBalanceSheetItem(@Valid @RequestBody BalanceSheetItemDTO balanceSheetItemDTO) throws URISyntaxException {
        log.debug("REST request to save BalanceSheetItem : {}", balanceSheetItemDTO);
        if (balanceSheetItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new balanceSheetItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BalanceSheetItemDTO result = balanceSheetItemService.save(balanceSheetItemDTO);
        return ResponseEntity.created(new URI("/api/balance-sheet-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /balance-sheet-items : Updates an existing balanceSheetItem.
     *
     * @param balanceSheetItemDTO the balanceSheetItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated balanceSheetItemDTO,
     * or with status 400 (Bad Request) if the balanceSheetItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the balanceSheetItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/balance-sheet-items")
    @Timed
    public ResponseEntity<BalanceSheetItemDTO> updateBalanceSheetItem(@Valid @RequestBody BalanceSheetItemDTO balanceSheetItemDTO) throws URISyntaxException {
        log.debug("REST request to update BalanceSheetItem : {}", balanceSheetItemDTO);
        if (balanceSheetItemDTO.getId() == null) {
            return createBalanceSheetItem(balanceSheetItemDTO);
        }
        BalanceSheetItemDTO result = balanceSheetItemService.save(balanceSheetItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, balanceSheetItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /balance-sheet-items : get all the balanceSheetItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of balanceSheetItems in body
     */
    @GetMapping("/balance-sheet-items")
    @Timed
    public ResponseEntity<List<BalanceSheetItemDTO>> getAllBalanceSheetItems(Pageable pageable) {
        log.debug("REST request to get a page of BalanceSheetItems");
        Page<BalanceSheetItemDTO> page = balanceSheetItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/balance-sheet-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /balance-sheet-items/:id : get the "id" balanceSheetItem.
     *
     * @param id the id of the balanceSheetItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the balanceSheetItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/balance-sheet-items/{id}")
    @Timed
    public ResponseEntity<BalanceSheetItemDTO> getBalanceSheetItem(@PathVariable Long id) {
        log.debug("REST request to get BalanceSheetItem : {}", id);
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(balanceSheetItemDTO));
    }

    /**
     * DELETE  /balance-sheet-items/:id : delete the "id" balanceSheetItem.
     *
     * @param id the id of the balanceSheetItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/balance-sheet-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteBalanceSheetItem(@PathVariable Long id) {
        log.debug("REST request to delete BalanceSheetItem : {}", id);
        balanceSheetItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
