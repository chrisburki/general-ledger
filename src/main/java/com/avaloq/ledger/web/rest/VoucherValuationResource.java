package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.VoucherValuationService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.web.rest.util.PaginationUtil;
import com.avaloq.ledger.service.dto.VoucherValuationDTO;
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
 * REST controller for managing VoucherValuation.
 */
@RestController
@RequestMapping("/api")
public class VoucherValuationResource {

    private final Logger log = LoggerFactory.getLogger(VoucherValuationResource.class);

    private static final String ENTITY_NAME = "voucherValuation";

    private final VoucherValuationService voucherValuationService;

    public VoucherValuationResource(VoucherValuationService voucherValuationService) {
        this.voucherValuationService = voucherValuationService;
    }

    /**
     * POST  /voucher-valuations : Create a new voucherValuation.
     *
     * @param voucherValuationDTO the voucherValuationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherValuationDTO, or with status 400 (Bad Request) if the voucherValuation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voucher-valuations")
    @Timed
    public ResponseEntity<VoucherValuationDTO> createVoucherValuation(@Valid @RequestBody VoucherValuationDTO voucherValuationDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherValuation : {}", voucherValuationDTO);
        if (voucherValuationDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherValuation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherValuationDTO result = voucherValuationService.save(voucherValuationDTO);
        return ResponseEntity.created(new URI("/api/voucher-valuations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voucher-valuations : Updates an existing voucherValuation.
     *
     * @param voucherValuationDTO the voucherValuationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherValuationDTO,
     * or with status 400 (Bad Request) if the voucherValuationDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherValuationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voucher-valuations")
    @Timed
    public ResponseEntity<VoucherValuationDTO> updateVoucherValuation(@Valid @RequestBody VoucherValuationDTO voucherValuationDTO) throws URISyntaxException {
        log.debug("REST request to update VoucherValuation : {}", voucherValuationDTO);
        if (voucherValuationDTO.getId() == null) {
            return createVoucherValuation(voucherValuationDTO);
        }
        VoucherValuationDTO result = voucherValuationService.save(voucherValuationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherValuationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voucher-valuations : get all the voucherValuations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of voucherValuations in body
     */
    @GetMapping("/voucher-valuations")
    @Timed
    public ResponseEntity<List<VoucherValuationDTO>> getAllVoucherValuations(Pageable pageable) {
        log.debug("REST request to get a page of VoucherValuations");
        Page<VoucherValuationDTO> page = voucherValuationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/voucher-valuations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /voucher-valuations/:id : get the "id" voucherValuation.
     *
     * @param id the id of the voucherValuationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherValuationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/voucher-valuations/{id}")
    @Timed
    public ResponseEntity<VoucherValuationDTO> getVoucherValuation(@PathVariable Long id) {
        log.debug("REST request to get VoucherValuation : {}", id);
        VoucherValuationDTO voucherValuationDTO = voucherValuationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(voucherValuationDTO));
    }

    /**
     * DELETE  /voucher-valuations/:id : delete the "id" voucherValuation.
     *
     * @param id the id of the voucherValuationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voucher-valuations/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoucherValuation(@PathVariable Long id) {
        log.debug("REST request to delete VoucherValuation : {}", id);
        voucherValuationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
