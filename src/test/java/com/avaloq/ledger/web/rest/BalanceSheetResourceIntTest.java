package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.BalanceSheet;
import com.avaloq.ledger.repository.BalanceSheetRepository;
import com.avaloq.ledger.service.BalanceSheetService;
import com.avaloq.ledger.service.dto.BalanceSheetDTO;
import com.avaloq.ledger.service.mapper.BalanceSheetMapper;
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

import com.avaloq.ledger.domain.enumeration.BalanceDateType;
/**
 * Test class for the BalanceSheetResource REST controller.
 *
 * @see BalanceSheetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class BalanceSheetResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BALANCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BALANCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BalanceDateType DEFAULT_BALANCE_DATE_TYPE = BalanceDateType.DONE;
    private static final BalanceDateType UPDATED_BALANCE_DATE_TYPE = BalanceDateType.BOOK;

    private static final Long DEFAULT_GLOBAL_SEQUENCE_NUMBER = 1L;
    private static final Long UPDATED_GLOBAL_SEQUENCE_NUMBER = 2L;

    private static final String DEFAULT_LEGAL_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private BalanceSheetRepository balanceSheetRepository;

    @Autowired
    private BalanceSheetMapper balanceSheetMapper;

    @Autowired
    private BalanceSheetService balanceSheetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBalanceSheetMockMvc;

    private BalanceSheet balanceSheet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BalanceSheetResource balanceSheetResource = new BalanceSheetResource(balanceSheetService);
        this.restBalanceSheetMockMvc = MockMvcBuilders.standaloneSetup(balanceSheetResource)
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
    public static BalanceSheet createEntity(EntityManager em) {
        BalanceSheet balanceSheet = new BalanceSheet()
            .description(DEFAULT_DESCRIPTION)
            .key(DEFAULT_KEY)
            .balanceDate(DEFAULT_BALANCE_DATE)
            .balanceDateType(DEFAULT_BALANCE_DATE_TYPE)
            .globalSequenceNumber(DEFAULT_GLOBAL_SEQUENCE_NUMBER)
            .legalEntityId(DEFAULT_LEGAL_ENTITY_ID);
        return balanceSheet;
    }

    @Before
    public void initTest() {
        balanceSheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createBalanceSheet() throws Exception {
        int databaseSizeBeforeCreate = balanceSheetRepository.findAll().size();

        // Create the BalanceSheet
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(balanceSheet);
        restBalanceSheetMockMvc.perform(post("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isCreated());

        // Validate the BalanceSheet in the database
        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeCreate + 1);
        BalanceSheet testBalanceSheet = balanceSheetList.get(balanceSheetList.size() - 1);
        assertThat(testBalanceSheet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBalanceSheet.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testBalanceSheet.getBalanceDate()).isEqualTo(DEFAULT_BALANCE_DATE);
        assertThat(testBalanceSheet.getBalanceDateType()).isEqualTo(DEFAULT_BALANCE_DATE_TYPE);
        assertThat(testBalanceSheet.getGlobalSequenceNumber()).isEqualTo(DEFAULT_GLOBAL_SEQUENCE_NUMBER);
        assertThat(testBalanceSheet.getLegalEntityId()).isEqualTo(DEFAULT_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createBalanceSheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = balanceSheetRepository.findAll().size();

        // Create the BalanceSheet with an existing ID
        balanceSheet.setId(1L);
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(balanceSheet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalanceSheetMockMvc.perform(post("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BalanceSheet in the database
        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetRepository.findAll().size();
        // set the field null
        balanceSheet.setKey(null);

        // Create the BalanceSheet, which fails.
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(balanceSheet);

        restBalanceSheetMockMvc.perform(post("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBalanceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetRepository.findAll().size();
        // set the field null
        balanceSheet.setBalanceDate(null);

        // Create the BalanceSheet, which fails.
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(balanceSheet);

        restBalanceSheetMockMvc.perform(post("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBalanceDateTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetRepository.findAll().size();
        // set the field null
        balanceSheet.setBalanceDateType(null);

        // Create the BalanceSheet, which fails.
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(balanceSheet);

        restBalanceSheetMockMvc.perform(post("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGlobalSequenceNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetRepository.findAll().size();
        // set the field null
        balanceSheet.setGlobalSequenceNumber(null);

        // Create the BalanceSheet, which fails.
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(balanceSheet);

        restBalanceSheetMockMvc.perform(post("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetRepository.findAll().size();
        // set the field null
        balanceSheet.setLegalEntityId(null);

        // Create the BalanceSheet, which fails.
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(balanceSheet);

        restBalanceSheetMockMvc.perform(post("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBalanceSheets() throws Exception {
        // Initialize the database
        balanceSheetRepository.saveAndFlush(balanceSheet);

        // Get all the balanceSheetList
        restBalanceSheetMockMvc.perform(get("/api/balance-sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balanceSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].balanceDate").value(hasItem(DEFAULT_BALANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].balanceDateType").value(hasItem(DEFAULT_BALANCE_DATE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].globalSequenceNumber").value(hasItem(DEFAULT_GLOBAL_SEQUENCE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].legalEntityId").value(hasItem(DEFAULT_LEGAL_ENTITY_ID.toString())));
    }

    @Test
    @Transactional
    public void getBalanceSheet() throws Exception {
        // Initialize the database
        balanceSheetRepository.saveAndFlush(balanceSheet);

        // Get the balanceSheet
        restBalanceSheetMockMvc.perform(get("/api/balance-sheets/{id}", balanceSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(balanceSheet.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.balanceDate").value(DEFAULT_BALANCE_DATE.toString()))
            .andExpect(jsonPath("$.balanceDateType").value(DEFAULT_BALANCE_DATE_TYPE.toString()))
            .andExpect(jsonPath("$.globalSequenceNumber").value(DEFAULT_GLOBAL_SEQUENCE_NUMBER.intValue()))
            .andExpect(jsonPath("$.legalEntityId").value(DEFAULT_LEGAL_ENTITY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBalanceSheet() throws Exception {
        // Get the balanceSheet
        restBalanceSheetMockMvc.perform(get("/api/balance-sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBalanceSheet() throws Exception {
        // Initialize the database
        balanceSheetRepository.saveAndFlush(balanceSheet);
        int databaseSizeBeforeUpdate = balanceSheetRepository.findAll().size();

        // Update the balanceSheet
        BalanceSheet updatedBalanceSheet = balanceSheetRepository.findOne(balanceSheet.getId());
        // Disconnect from session so that the updates on updatedBalanceSheet are not directly saved in db
        em.detach(updatedBalanceSheet);
        updatedBalanceSheet
            .description(UPDATED_DESCRIPTION)
            .key(UPDATED_KEY)
            .balanceDate(UPDATED_BALANCE_DATE)
            .balanceDateType(UPDATED_BALANCE_DATE_TYPE)
            .globalSequenceNumber(UPDATED_GLOBAL_SEQUENCE_NUMBER)
            .legalEntityId(UPDATED_LEGAL_ENTITY_ID);
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(updatedBalanceSheet);

        restBalanceSheetMockMvc.perform(put("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isOk());

        // Validate the BalanceSheet in the database
        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeUpdate);
        BalanceSheet testBalanceSheet = balanceSheetList.get(balanceSheetList.size() - 1);
        assertThat(testBalanceSheet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBalanceSheet.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testBalanceSheet.getBalanceDate()).isEqualTo(UPDATED_BALANCE_DATE);
        assertThat(testBalanceSheet.getBalanceDateType()).isEqualTo(UPDATED_BALANCE_DATE_TYPE);
        assertThat(testBalanceSheet.getGlobalSequenceNumber()).isEqualTo(UPDATED_GLOBAL_SEQUENCE_NUMBER);
        assertThat(testBalanceSheet.getLegalEntityId()).isEqualTo(UPDATED_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBalanceSheet() throws Exception {
        int databaseSizeBeforeUpdate = balanceSheetRepository.findAll().size();

        // Create the BalanceSheet
        BalanceSheetDTO balanceSheetDTO = balanceSheetMapper.toDto(balanceSheet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBalanceSheetMockMvc.perform(put("/api/balance-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetDTO)))
            .andExpect(status().isCreated());

        // Validate the BalanceSheet in the database
        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBalanceSheet() throws Exception {
        // Initialize the database
        balanceSheetRepository.saveAndFlush(balanceSheet);
        int databaseSizeBeforeDelete = balanceSheetRepository.findAll().size();

        // Get the balanceSheet
        restBalanceSheetMockMvc.perform(delete("/api/balance-sheets/{id}", balanceSheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BalanceSheet> balanceSheetList = balanceSheetRepository.findAll();
        assertThat(balanceSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceSheet.class);
        BalanceSheet balanceSheet1 = new BalanceSheet();
        balanceSheet1.setId(1L);
        BalanceSheet balanceSheet2 = new BalanceSheet();
        balanceSheet2.setId(balanceSheet1.getId());
        assertThat(balanceSheet1).isEqualTo(balanceSheet2);
        balanceSheet2.setId(2L);
        assertThat(balanceSheet1).isNotEqualTo(balanceSheet2);
        balanceSheet1.setId(null);
        assertThat(balanceSheet1).isNotEqualTo(balanceSheet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceSheetDTO.class);
        BalanceSheetDTO balanceSheetDTO1 = new BalanceSheetDTO();
        balanceSheetDTO1.setId(1L);
        BalanceSheetDTO balanceSheetDTO2 = new BalanceSheetDTO();
        assertThat(balanceSheetDTO1).isNotEqualTo(balanceSheetDTO2);
        balanceSheetDTO2.setId(balanceSheetDTO1.getId());
        assertThat(balanceSheetDTO1).isEqualTo(balanceSheetDTO2);
        balanceSheetDTO2.setId(2L);
        assertThat(balanceSheetDTO1).isNotEqualTo(balanceSheetDTO2);
        balanceSheetDTO1.setId(null);
        assertThat(balanceSheetDTO1).isNotEqualTo(balanceSheetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(balanceSheetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(balanceSheetMapper.fromId(null)).isNull();
    }
}
