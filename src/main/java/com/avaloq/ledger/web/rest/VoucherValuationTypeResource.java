package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.domain.VoucherValuationType;

import com.avaloq.ledger.repository.VoucherValuationTypeRepository;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.service.dto.VoucherValuationTypeDTO;
import com.avaloq.ledger.service.mapper.VoucherValuationTypeMapper;
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
 * REST controller for managing VoucherValuationType.
 */
@RestController
@RequestMapping("/api")
public class VoucherValuationTypeResource {

    private final Logger log = LoggerFactory.getLogger(VoucherValuationTypeResource.class);

    private static final String ENTITY_NAME = "voucherValuationType";

    private final VoucherValuationTypeRepository voucherValuationTypeRepository;

    private final VoucherValuationTypeMapper voucherValuationTypeMapper;

    public VoucherValuationTypeResource(VoucherValuationTypeRepository voucherValuationTypeRepository, VoucherValuationTypeMapper voucherValuationTypeMapper) {
        this.voucherValuationTypeRepository = voucherValuationTypeRepository;
        this.voucherValuationTypeMapper = voucherValuationTypeMapper;
    }

    /**
     * POST  /voucher-valuation-types : Create a new voucherValuationType.
     *
     * @param voucherValuationTypeDTO the voucherValuationTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherValuationTypeDTO, or with status 400 (Bad Request) if the voucherValuationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voucher-valuation-types")
    @Timed
    public ResponseEntity<VoucherValuationTypeDTO> createVoucherValuationType(@Valid @RequestBody VoucherValuationTypeDTO voucherValuationTypeDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherValuationType : {}", voucherValuationTypeDTO);
        if (voucherValuationTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherValuationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherValuationType voucherValuationType = voucherValuationTypeMapper.toEntity(voucherValuationTypeDTO);
        voucherValuationType = voucherValuationTypeRepository.save(voucherValuationType);
        VoucherValuationTypeDTO result = voucherValuationTypeMapper.toDto(voucherValuationType);
        return ResponseEntity.created(new URI("/api/voucher-valuation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voucher-valuation-types : Updates an existing voucherValuationType.
     *
     * @param voucherValuationTypeDTO the voucherValuationTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherValuationTypeDTO,
     * or with status 400 (Bad Request) if the voucherValuationTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherValuationTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voucher-valuation-types")
    @Timed
    public ResponseEntity<VoucherValuationTypeDTO> updateVoucherValuationType(@Valid @RequestBody VoucherValuationTypeDTO voucherValuationTypeDTO) throws URISyntaxException {
        log.debug("REST request to update VoucherValuationType : {}", voucherValuationTypeDTO);
        if (voucherValuationTypeDTO.getId() == null) {
            return createVoucherValuationType(voucherValuationTypeDTO);
        }
        VoucherValuationType voucherValuationType = voucherValuationTypeMapper.toEntity(voucherValuationTypeDTO);
        voucherValuationType = voucherValuationTypeRepository.save(voucherValuationType);
        VoucherValuationTypeDTO result = voucherValuationTypeMapper.toDto(voucherValuationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherValuationTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voucher-valuation-types : get all the voucherValuationTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of voucherValuationTypes in body
     */
    @GetMapping("/voucher-valuation-types")
    @Timed
    public List<VoucherValuationTypeDTO> getAllVoucherValuationTypes() {
        log.debug("REST request to get all VoucherValuationTypes");
        List<VoucherValuationType> voucherValuationTypes = voucherValuationTypeRepository.findAll();
        return voucherValuationTypeMapper.toDto(voucherValuationTypes);
        }

    /**
     * GET  /voucher-valuation-types/:id : get the "id" voucherValuationType.
     *
     * @param id the id of the voucherValuationTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherValuationTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/voucher-valuation-types/{id}")
    @Timed
    public ResponseEntity<VoucherValuationTypeDTO> getVoucherValuationType(@PathVariable Long id) {
        log.debug("REST request to get VoucherValuationType : {}", id);
        VoucherValuationType voucherValuationType = voucherValuationTypeRepository.findOne(id);
        VoucherValuationTypeDTO voucherValuationTypeDTO = voucherValuationTypeMapper.toDto(voucherValuationType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(voucherValuationTypeDTO));
    }

    /**
     * DELETE  /voucher-valuation-types/:id : delete the "id" voucherValuationType.
     *
     * @param id the id of the voucherValuationTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voucher-valuation-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoucherValuationType(@PathVariable Long id) {
        log.debug("REST request to delete VoucherValuationType : {}", id);
        voucherValuationTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
