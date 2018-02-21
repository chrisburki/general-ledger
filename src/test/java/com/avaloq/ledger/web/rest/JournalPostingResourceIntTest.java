package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.JournalPosting;
import com.avaloq.ledger.repository.JournalPostingRepository;
import com.avaloq.ledger.service.JournalPostingService;
import com.avaloq.ledger.service.dto.JournalPostingDTO;
import com.avaloq.ledger.service.mapper.JournalPostingMapper;
import com.avaloq.ledger.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.avaloq.ledger.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JournalPostingResource REST controller.
 *
 * @see JournalPostingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class JournalPostingResourceIntTest {

    private static final LocalDate DEFAULT_BOOK_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BOOK_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_CURRENCY_ISO = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ISO = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT_CURRENCY = 1D;
    private static final Double UPDATED_AMOUNT_CURRENCY = 2D;

    private static final String DEFAULT_BOOKING_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_BOOKING_TEXT = "BBBBBBBBBB";

    private static final Long DEFAULT_GLOBAL_SEQUENCE_NUMBER = 1L;
    private static final Long UPDATED_GLOBAL_SEQUENCE_NUMBER = 2L;

    private static final String DEFAULT_LEGAL_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private JournalPostingRepository journalPostingRepository;

    @Autowired
    private JournalPostingMapper journalPostingMapper;

    @Autowired
    private JournalPostingService journalPostingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJournalPostingMockMvc;

    private JournalPosting journalPosting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JournalPostingResource journalPostingResource = new JournalPostingResource(journalPostingService);
        this.restJournalPostingMockMvc = MockMvcBuilders.standaloneSetup(journalPostingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JournalPosting createEntity(EntityManager em) {
        JournalPosting journalPosting = new JournalPosting()
            .bookDate(DEFAULT_BOOK_DATE)
            .documentNumber(DEFAULT_DOCUMENT_NUMBER)
            .amount(DEFAULT_AMOUNT)
            .currencyIso(DEFAULT_CURRENCY_ISO)
            .amountCurrency(DEFAULT_AMOUNT_CURRENCY)
            .bookingText(DEFAULT_BOOKING_TEXT)
            .globalSequenceNumber(DEFAULT_GLOBAL_SEQUENCE_NUMBER)
            .legalEntityId(DEFAULT_LEGAL_ENTITY_ID);
        return journalPosting;
    }

    @Before
    public void initTest() {
        journalPosting = createEntity(em);
    }

    @Test
    @Transactional
    public void createJournalPosting() throws Exception {
        int databaseSizeBeforeCreate = journalPostingRepository.findAll().size();

        // Create the JournalPosting
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(journalPosting);
        restJournalPostingMockMvc.perform(post("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isCreated());

        // Validate the JournalPosting in the database
        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeCreate + 1);
        JournalPosting testJournalPosting = journalPostingList.get(journalPostingList.size() - 1);
        assertThat(testJournalPosting.getBookDate()).isEqualTo(DEFAULT_BOOK_DATE);
        assertThat(testJournalPosting.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testJournalPosting.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testJournalPosting.getCurrencyIso()).isEqualTo(DEFAULT_CURRENCY_ISO);
        assertThat(testJournalPosting.getAmountCurrency()).isEqualTo(DEFAULT_AMOUNT_CURRENCY);
        assertThat(testJournalPosting.getBookingText()).isEqualTo(DEFAULT_BOOKING_TEXT);
        assertThat(testJournalPosting.getGlobalSequenceNumber()).isEqualTo(DEFAULT_GLOBAL_SEQUENCE_NUMBER);
        assertThat(testJournalPosting.getLegalEntityId()).isEqualTo(DEFAULT_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createJournalPostingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journalPostingRepository.findAll().size();

        // Create the JournalPosting with an existing ID
        journalPosting.setId(1L);
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(journalPosting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJournalPostingMockMvc.perform(post("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JournalPosting in the database
        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBookDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalPostingRepository.findAll().size();
        // set the field null
        journalPosting.setBookDate(null);

        // Create the JournalPosting, which fails.
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(journalPosting);

        restJournalPostingMockMvc.perform(post("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isBadRequest());

        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalPostingRepository.findAll().size();
        // set the field null
        journalPosting.setDocumentNumber(null);

        // Create the JournalPosting, which fails.
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(journalPosting);

        restJournalPostingMockMvc.perform(post("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isBadRequest());

        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalPostingRepository.findAll().size();
        // set the field null
        journalPosting.setAmount(null);

        // Create the JournalPosting, which fails.
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(journalPosting);

        restJournalPostingMockMvc.perform(post("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isBadRequest());

        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGlobalSequenceNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalPostingRepository.findAll().size();
        // set the field null
        journalPosting.setGlobalSequenceNumber(null);

        // Create the JournalPosting, which fails.
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(journalPosting);

        restJournalPostingMockMvc.perform(post("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isBadRequest());

        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalPostingRepository.findAll().size();
        // set the field null
        journalPosting.setLegalEntityId(null);

        // Create the JournalPosting, which fails.
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(journalPosting);

        restJournalPostingMockMvc.perform(post("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isBadRequest());

        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJournalPostings() throws Exception {
        // Initialize the database
        journalPostingRepository.saveAndFlush(journalPosting);

        // Get all the journalPostingList
        restJournalPostingMockMvc.perform(get("/api/journal-postings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journalPosting.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookDate").value(hasItem(DEFAULT_BOOK_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyIso").value(hasItem(DEFAULT_CURRENCY_ISO.toString())))
            .andExpect(jsonPath("$.[*].amountCurrency").value(hasItem(DEFAULT_AMOUNT_CURRENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].bookingText").value(hasItem(DEFAULT_BOOKING_TEXT.toString())))
            .andExpect(jsonPath("$.[*].globalSequenceNumber").value(hasItem(DEFAULT_GLOBAL_SEQUENCE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].legalEntityId").value(hasItem(DEFAULT_LEGAL_ENTITY_ID.toString())));
    }

    @Test
    @Transactional
    public void getJournalPosting() throws Exception {
        // Initialize the database
        journalPostingRepository.saveAndFlush(journalPosting);

        // Get the journalPosting
        restJournalPostingMockMvc.perform(get("/api/journal-postings/{id}", journalPosting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journalPosting.getId().intValue()))
            .andExpect(jsonPath("$.bookDate").value(DEFAULT_BOOK_DATE.toString()))
            .andExpect(jsonPath("$.documentNumber").value(DEFAULT_DOCUMENT_NUMBER.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currencyIso").value(DEFAULT_CURRENCY_ISO.toString()))
            .andExpect(jsonPath("$.amountCurrency").value(DEFAULT_AMOUNT_CURRENCY.doubleValue()))
            .andExpect(jsonPath("$.bookingText").value(DEFAULT_BOOKING_TEXT.toString()))
            .andExpect(jsonPath("$.globalSequenceNumber").value(DEFAULT_GLOBAL_SEQUENCE_NUMBER.intValue()))
            .andExpect(jsonPath("$.legalEntityId").value(DEFAULT_LEGAL_ENTITY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJournalPosting() throws Exception {
        // Get the journalPosting
        restJournalPostingMockMvc.perform(get("/api/journal-postings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJournalPosting() throws Exception {
        // Initialize the database
        journalPostingRepository.saveAndFlush(journalPosting);
        int databaseSizeBeforeUpdate = journalPostingRepository.findAll().size();

        // Update the journalPosting
        JournalPosting updatedJournalPosting = journalPostingRepository.findOne(journalPosting.getId());
        // Disconnect from session so that the updates on updatedJournalPosting are not directly saved in db
        em.detach(updatedJournalPosting);
        updatedJournalPosting
            .bookDate(UPDATED_BOOK_DATE)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .amount(UPDATED_AMOUNT)
            .currencyIso(UPDATED_CURRENCY_ISO)
            .amountCurrency(UPDATED_AMOUNT_CURRENCY)
            .bookingText(UPDATED_BOOKING_TEXT)
            .globalSequenceNumber(UPDATED_GLOBAL_SEQUENCE_NUMBER)
            .legalEntityId(UPDATED_LEGAL_ENTITY_ID);
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(updatedJournalPosting);

        restJournalPostingMockMvc.perform(put("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isOk());

        // Validate the JournalPosting in the database
        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeUpdate);
        JournalPosting testJournalPosting = journalPostingList.get(journalPostingList.size() - 1);
        assertThat(testJournalPosting.getBookDate()).isEqualTo(UPDATED_BOOK_DATE);
        assertThat(testJournalPosting.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testJournalPosting.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testJournalPosting.getCurrencyIso()).isEqualTo(UPDATED_CURRENCY_ISO);
        assertThat(testJournalPosting.getAmountCurrency()).isEqualTo(UPDATED_AMOUNT_CURRENCY);
        assertThat(testJournalPosting.getBookingText()).isEqualTo(UPDATED_BOOKING_TEXT);
        assertThat(testJournalPosting.getGlobalSequenceNumber()).isEqualTo(UPDATED_GLOBAL_SEQUENCE_NUMBER);
        assertThat(testJournalPosting.getLegalEntityId()).isEqualTo(UPDATED_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingJournalPosting() throws Exception {
        int databaseSizeBeforeUpdate = journalPostingRepository.findAll().size();

        // Create the JournalPosting
        JournalPostingDTO journalPostingDTO = journalPostingMapper.toDto(journalPosting);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJournalPostingMockMvc.perform(put("/api/journal-postings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalPostingDTO)))
            .andExpect(status().isCreated());

        // Validate the JournalPosting in the database
        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJournalPosting() throws Exception {
        // Initialize the database
        journalPostingRepository.saveAndFlush(journalPosting);
        int databaseSizeBeforeDelete = journalPostingRepository.findAll().size();

        // Get the journalPosting
        restJournalPostingMockMvc.perform(delete("/api/journal-postings/{id}", journalPosting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JournalPosting> journalPostingList = journalPostingRepository.findAll();
        assertThat(journalPostingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalPosting.class);
        JournalPosting journalPosting1 = new JournalPosting();
        journalPosting1.setId(1L);
        JournalPosting journalPosting2 = new JournalPosting();
        journalPosting2.setId(journalPosting1.getId());
        assertThat(journalPosting1).isEqualTo(journalPosting2);
        journalPosting2.setId(2L);
        assertThat(journalPosting1).isNotEqualTo(journalPosting2);
        journalPosting1.setId(null);
        assertThat(journalPosting1).isNotEqualTo(journalPosting2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalPostingDTO.class);
        JournalPostingDTO journalPostingDTO1 = new JournalPostingDTO();
        journalPostingDTO1.setId(1L);
        JournalPostingDTO journalPostingDTO2 = new JournalPostingDTO();
        assertThat(journalPostingDTO1).isNotEqualTo(journalPostingDTO2);
        journalPostingDTO2.setId(journalPostingDTO1.getId());
        assertThat(journalPostingDTO1).isEqualTo(journalPostingDTO2);
        journalPostingDTO2.setId(2L);
        assertThat(journalPostingDTO1).isNotEqualTo(journalPostingDTO2);
        journalPostingDTO1.setId(null);
        assertThat(journalPostingDTO1).isNotEqualTo(journalPostingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(journalPostingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(journalPostingMapper.fromId(null)).isNull();
    }
}
