package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.VoucherBookingService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.web.rest.util.PaginationUtil;
import com.avaloq.ledger.service.dto.VoucherBookingDTO;
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
 * REST controller for managing VoucherBooking.
 */
@RestController
@RequestMapping("/api")
public class VoucherBookingResource {

    private final Logger log = LoggerFactory.getLogger(VoucherBookingResource.class);

    private static final String ENTITY_NAME = "voucherBooking";

    private final VoucherBookingService voucherBookingService;

    public VoucherBookingResource(VoucherBookingService voucherBookingService) {
        this.voucherBookingService = voucherBookingService;
    }

    /**
     * POST  /voucher-bookings : Create a new voucherBooking.
     *
     * @param voucherBookingDTO the voucherBookingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherBookingDTO, or with status 400 (Bad Request) if the voucherBooking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voucher-bookings")
    @Timed
    public ResponseEntity<VoucherBookingDTO> createVoucherBooking(@Valid @RequestBody VoucherBookingDTO voucherBookingDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherBooking : {}", voucherBookingDTO);
        if (voucherBookingDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherBooking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherBookingDTO result = voucherBookingService.save(voucherBookingDTO);
        return ResponseEntity.created(new URI("/api/voucher-bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voucher-bookings : Updates an existing voucherBooking.
     *
     * @param voucherBookingDTO the voucherBookingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherBookingDTO,
     * or with status 400 (Bad Request) if the voucherBookingDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherBookingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voucher-bookings")
    @Timed
    public ResponseEntity<VoucherBookingDTO> updateVoucherBooking(@Valid @RequestBody VoucherBookingDTO voucherBookingDTO) throws URISyntaxException {
        log.debug("REST request to update VoucherBooking : {}", voucherBookingDTO);
        if (voucherBookingDTO.getId() == null) {
            return createVoucherBooking(voucherBookingDTO);
        }
        VoucherBookingDTO result = voucherBookingService.save(voucherBookingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherBookingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voucher-bookings : get all the voucherBookings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of voucherBookings in body
     */
    @GetMapping("/voucher-bookings")
    @Timed
    public ResponseEntity<List<VoucherBookingDTO>> getAllVoucherBookings(Pageable pageable) {
        log.debug("REST request to get a page of VoucherBookings");
        Page<VoucherBookingDTO> page = voucherBookingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/voucher-bookings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /voucher-bookings/:id : get the "id" voucherBooking.
     *
     * @param id the id of the voucherBookingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherBookingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/voucher-bookings/{id}")
    @Timed
    public ResponseEntity<VoucherBookingDTO> getVoucherBooking(@PathVariable Long id) {
        log.debug("REST request to get VoucherBooking : {}", id);
        VoucherBookingDTO voucherBookingDTO = voucherBookingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(voucherBookingDTO));
    }

    /**
     * DELETE  /voucher-bookings/:id : delete the "id" voucherBooking.
     *
     * @param id the id of the voucherBookingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voucher-bookings/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoucherBooking(@PathVariable Long id) {
        log.debug("REST request to delete VoucherBooking : {}", id);
        voucherBookingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
