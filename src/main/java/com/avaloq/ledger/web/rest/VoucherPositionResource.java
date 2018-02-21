package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.VoucherPositionService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.web.rest.util.PaginationUtil;
import com.avaloq.ledger.service.dto.VoucherPositionDTO;
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
 * REST controller for managing VoucherPosition.
 */
@RestController
@RequestMapping("/api")
public class VoucherPositionResource {

    private final Logger log = LoggerFactory.getLogger(VoucherPositionResource.class);

    private static final String ENTITY_NAME = "voucherPosition";

    private final VoucherPositionService voucherPositionService;

    public VoucherPositionResource(VoucherPositionService voucherPositionService) {
        this.voucherPositionService = voucherPositionService;
    }

    /**
     * POST  /voucher-positions : Create a new voucherPosition.
     *
     * @param voucherPositionDTO the voucherPositionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherPositionDTO, or with status 400 (Bad Request) if the voucherPosition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voucher-positions")
    @Timed
    public ResponseEntity<VoucherPositionDTO> createVoucherPosition(@Valid @RequestBody VoucherPositionDTO voucherPositionDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherPosition : {}", voucherPositionDTO);
        if (voucherPositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherPositionDTO result = voucherPositionService.save(voucherPositionDTO);
        return ResponseEntity.created(new URI("/api/voucher-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voucher-positions : Updates an existing voucherPosition.
     *
     * @param voucherPositionDTO the voucherPositionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherPositionDTO,
     * or with status 400 (Bad Request) if the voucherPositionDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherPositionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voucher-positions")
    @Timed
    public ResponseEntity<VoucherPositionDTO> updateVoucherPosition(@Valid @RequestBody VoucherPositionDTO voucherPositionDTO) throws URISyntaxException {
        log.debug("REST request to update VoucherPosition : {}", voucherPositionDTO);
        if (voucherPositionDTO.getId() == null) {
            return createVoucherPosition(voucherPositionDTO);
        }
        VoucherPositionDTO result = voucherPositionService.save(voucherPositionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherPositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voucher-positions : get all the voucherPositions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of voucherPositions in body
     */
    @GetMapping("/voucher-positions")
    @Timed
    public ResponseEntity<List<VoucherPositionDTO>> getAllVoucherPositions(Pageable pageable) {
        log.debug("REST request to get a page of VoucherPositions");
        Page<VoucherPositionDTO> page = voucherPositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/voucher-positions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /voucher-positions/:id : get the "id" voucherPosition.
     *
     * @param id the id of the voucherPositionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherPositionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/voucher-positions/{id}")
    @Timed
    public ResponseEntity<VoucherPositionDTO> getVoucherPosition(@PathVariable Long id) {
        log.debug("REST request to get VoucherPosition : {}", id);
        VoucherPositionDTO voucherPositionDTO = voucherPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(voucherPositionDTO));
    }

    /**
     * DELETE  /voucher-positions/:id : delete the "id" voucherPosition.
     *
     * @param id the id of the voucherPositionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voucher-positions/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoucherPosition(@PathVariable Long id) {
        log.debug("REST request to delete VoucherPosition : {}", id);
        voucherPositionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
