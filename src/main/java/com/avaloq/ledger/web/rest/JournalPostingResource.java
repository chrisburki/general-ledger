package com.avaloq.ledger.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaloq.ledger.service.JournalPostingService;
import com.avaloq.ledger.web.rest.errors.BadRequestAlertException;
import com.avaloq.ledger.web.rest.util.HeaderUtil;
import com.avaloq.ledger.web.rest.util.PaginationUtil;
import com.avaloq.ledger.service.dto.JournalPostingDTO;
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
 * REST controller for managing JournalPosting.
 */
@RestController
@RequestMapping("/api")
public class JournalPostingResource {

    private final Logger log = LoggerFactory.getLogger(JournalPostingResource.class);

    private static final String ENTITY_NAME = "journalPosting";

    private final JournalPostingService journalPostingService;

    public JournalPostingResource(JournalPostingService journalPostingService) {
        this.journalPostingService = journalPostingService;
    }

    /**
     * POST  /journal-postings : Create a new journalPosting.
     *
     * @param journalPostingDTO the journalPostingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journalPostingDTO, or with status 400 (Bad Request) if the journalPosting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/journal-postings")
    @Timed
    public ResponseEntity<JournalPostingDTO> createJournalPosting(@Valid @RequestBody JournalPostingDTO journalPostingDTO) throws URISyntaxException {
        log.debug("REST request to save JournalPosting : {}", journalPostingDTO);
        if (journalPostingDTO.getId() != null) {
            throw new BadRequestAlertException("A new journalPosting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JournalPostingDTO result = journalPostingService.save(journalPostingDTO);
        return ResponseEntity.created(new URI("/api/journal-postings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /journal-postings : Updates an existing journalPosting.
     *
     * @param journalPostingDTO the journalPostingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated journalPostingDTO,
     * or with status 400 (Bad Request) if the journalPostingDTO is not valid,
     * or with status 500 (Internal Server Error) if the journalPostingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/journal-postings")
    @Timed
    public ResponseEntity<JournalPostingDTO> updateJournalPosting(@Valid @RequestBody JournalPostingDTO journalPostingDTO) throws URISyntaxException {
        log.debug("REST request to update JournalPosting : {}", journalPostingDTO);
        if (journalPostingDTO.getId() == null) {
            return createJournalPosting(journalPostingDTO);
        }
        JournalPostingDTO result = journalPostingService.save(journalPostingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, journalPostingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /journal-postings : get all the journalPostings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of journalPostings in body
     */
    @GetMapping("/journal-postings")
    @Timed
    public ResponseEntity<List<JournalPostingDTO>> getAllJournalPostings(Pageable pageable) {
        log.debug("REST request to get a page of JournalPostings");
        Page<JournalPostingDTO> page = journalPostingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/journal-postings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /journal-postings/:id : get the "id" journalPosting.
     *
     * @param id the id of the journalPostingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalPostingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/journal-postings/{id}")
    @Timed
    public ResponseEntity<JournalPostingDTO> getJournalPosting(@PathVariable Long id) {
        log.debug("REST request to get JournalPosting : {}", id);
        JournalPostingDTO journalPostingDTO = journalPostingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(journalPostingDTO));
    }

    /**
     * DELETE  /journal-postings/:id : delete the "id" journalPosting.
     *
     * @param id the id of the journalPostingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/journal-postings/{id}")
    @Timed
    public ResponseEntity<Void> deleteJournalPosting(@PathVariable Long id) {
        log.debug("REST request to delete JournalPosting : {}", id);
        journalPostingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
