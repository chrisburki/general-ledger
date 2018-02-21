package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.domain.VoucherAccountType;

import com.avaloq.ledger.repository.VoucherAccountTypeRepository;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.service.dto.VoucherAccountTypeDTO;
import com.avaloq.ledger.service.mapper.VoucherAccountTypeMapper;
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
 * REST controller for managing VoucherAccountType.
 */
@RestController
@RequestMapping("/api")
public class VoucherAccountTypeResource {

    private final Logger log = LoggerFactory.getLogger(VoucherAccountTypeResource.class);

    private static final String ENTITY_NAME = "voucherAccountType";

    private final VoucherAccountTypeRepository voucherAccountTypeRepository;

    private final VoucherAccountTypeMapper voucherAccountTypeMapper;

    public VoucherAccountTypeResource(VoucherAccountTypeRepository voucherAccountTypeRepository, VoucherAccountTypeMapper voucherAccountTypeMapper) {
        this.voucherAccountTypeRepository = voucherAccountTypeRepository;
        this.voucherAccountTypeMapper = voucherAccountTypeMapper;
    }

    /**
     * POST  /voucher-account-types : Create a new voucherAccountType.
     *
     * @param voucherAccountTypeDTO the voucherAccountTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherAccountTypeDTO, or with status 400 (Bad Request) if the voucherAccountType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voucher-account-types")
    @Timed
    public ResponseEntity<VoucherAccountTypeDTO> createVoucherAccountType(@Valid @RequestBody VoucherAccountTypeDTO voucherAccountTypeDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherAccountType : {}", voucherAccountTypeDTO);
        if (voucherAccountTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherAccountType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherAccountType voucherAccountType = voucherAccountTypeMapper.toEntity(voucherAccountTypeDTO);
        voucherAccountType = voucherAccountTypeRepository.save(voucherAccountType);
        VoucherAccountTypeDTO result = voucherAccountTypeMapper.toDto(voucherAccountType);
        return ResponseEntity.created(new URI("/api/voucher-account-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voucher-account-types : Updates an existing voucherAccountType.
     *
     * @param voucherAccountTypeDTO the voucherAccountTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherAccountTypeDTO,
     * or with status 400 (Bad Request) if the voucherAccountTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherAccountTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voucher-account-types")
    @Timed
    public ResponseEntity<VoucherAccountTypeDTO> updateVoucherAccountType(@Valid @RequestBody VoucherAccountTypeDTO voucherAccountTypeDTO) throws URISyntaxException {
        log.debug("REST request to update VoucherAccountType : {}", voucherAccountTypeDTO);
        if (voucherAccountTypeDTO.getId() == null) {
            return createVoucherAccountType(voucherAccountTypeDTO);
        }
        VoucherAccountType voucherAccountType = voucherAccountTypeMapper.toEntity(voucherAccountTypeDTO);
        voucherAccountType = voucherAccountTypeRepository.save(voucherAccountType);
        VoucherAccountTypeDTO result = voucherAccountTypeMapper.toDto(voucherAccountType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherAccountTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voucher-account-types : get all the voucherAccountTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of voucherAccountTypes in body
     */
    @GetMapping("/voucher-account-types")
    @Timed
    public List<VoucherAccountTypeDTO> getAllVoucherAccountTypes() {
        log.debug("REST request to get all VoucherAccountTypes");
        List<VoucherAccountType> voucherAccountTypes = voucherAccountTypeRepository.findAll();
        return voucherAccountTypeMapper.toDto(voucherAccountTypes);
        }

    /**
     * GET  /voucher-account-types/:id : get the "id" voucherAccountType.
     *
     * @param id the id of the voucherAccountTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherAccountTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/voucher-account-types/{id}")
    @Timed
    public ResponseEntity<VoucherAccountTypeDTO> getVoucherAccountType(@PathVariable Long id) {
        log.debug("REST request to get VoucherAccountType : {}", id);
        VoucherAccountType voucherAccountType = voucherAccountTypeRepository.findOne(id);
        VoucherAccountTypeDTO voucherAccountTypeDTO = voucherAccountTypeMapper.toDto(voucherAccountType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(voucherAccountTypeDTO));
    }

    /**
     * DELETE  /voucher-account-types/:id : delete the "id" voucherAccountType.
     *
     * @param id the id of the voucherAccountTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voucher-account-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoucherAccountType(@PathVariable Long id) {
        log.debug("REST request to delete VoucherAccountType : {}", id);
        voucherAccountTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
