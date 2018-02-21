package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.ChartOfAccounts;
import com.avaloq.ledger.repository.ChartOfAccountsRepository;
import com.avaloq.ledger.service.ChartOfAccountsService;
import com.avaloq.ledger.service.dto.ChartOfAccountsDTO;
import com.avaloq.ledger.service.mapper.ChartOfAccountsMapper;
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
import java.util.List;

import static com.avaloq.ledger.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.avaloq.ledger.domain.enumeration.AccountingStandard;
/**
 * Test class for the ChartOfAccountsResource REST controller.
 *
 * @see ChartOfAccountsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class ChartOfAccountsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final AccountingStandard DEFAULT_ACCOUNTING_STANDARD = AccountingStandard.IFRS;
    private static final AccountingStandard UPDATED_ACCOUNTING_STANDARD = AccountingStandard.HGB;

    private static final String DEFAULT_BASE_CURRENCY_ISO = "AAAAAAAAAA";
    private static final String UPDATED_BASE_CURRENCY_ISO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_MAIN = false;
    private static final Boolean UPDATED_IS_MAIN = true;

    private static final String DEFAULT_LEGAL_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private ChartOfAccountsRepository chartOfAccountsRepository;

    @Autowired
    private ChartOfAccountsMapper chartOfAccountsMapper;

    @Autowired
    private ChartOfAccountsService chartOfAccountsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChartOfAccountsMockMvc;

    private ChartOfAccounts chartOfAccounts;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChartOfAccountsResource chartOfAccountsResource = new ChartOfAccountsResource(chartOfAccountsService);
        this.restChartOfAccountsMockMvc = MockMvcBuilders.standaloneSetup(chartOfAccountsResource)
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
    public static ChartOfAccounts createEntity(EntityManager em) {
        ChartOfAccounts chartOfAccounts = new ChartOfAccounts()
            .name(DEFAULT_NAME)
            .key(DEFAULT_KEY)
            .accountingStandard(DEFAULT_ACCOUNTING_STANDARD)
            .baseCurrencyIso(DEFAULT_BASE_CURRENCY_ISO)
            .isMain(DEFAULT_IS_MAIN)
            .legalEntityId(DEFAULT_LEGAL_ENTITY_ID);
        return chartOfAccounts;
    }

    @Before
    public void initTest() {
        chartOfAccounts = createEntity(em);
    }

    @Test
    @Transactional
    public void createChartOfAccounts() throws Exception {
        int databaseSizeBeforeCreate = chartOfAccountsRepository.findAll().size();

        // Create the ChartOfAccounts
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(chartOfAccounts);
        restChartOfAccountsMockMvc.perform(post("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isCreated());

        // Validate the ChartOfAccounts in the database
        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeCreate + 1);
        ChartOfAccounts testChartOfAccounts = chartOfAccountsList.get(chartOfAccountsList.size() - 1);
        assertThat(testChartOfAccounts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChartOfAccounts.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testChartOfAccounts.getAccountingStandard()).isEqualTo(DEFAULT_ACCOUNTING_STANDARD);
        assertThat(testChartOfAccounts.getBaseCurrencyIso()).isEqualTo(DEFAULT_BASE_CURRENCY_ISO);
        assertThat(testChartOfAccounts.isIsMain()).isEqualTo(DEFAULT_IS_MAIN);
        assertThat(testChartOfAccounts.getLegalEntityId()).isEqualTo(DEFAULT_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createChartOfAccountsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chartOfAccountsRepository.findAll().size();

        // Create the ChartOfAccounts with an existing ID
        chartOfAccounts.setId(1L);
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(chartOfAccounts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChartOfAccountsMockMvc.perform(post("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChartOfAccounts in the database
        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartOfAccountsRepository.findAll().size();
        // set the field null
        chartOfAccounts.setName(null);

        // Create the ChartOfAccounts, which fails.
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(chartOfAccounts);

        restChartOfAccountsMockMvc.perform(post("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartOfAccountsRepository.findAll().size();
        // set the field null
        chartOfAccounts.setKey(null);

        // Create the ChartOfAccounts, which fails.
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(chartOfAccounts);

        restChartOfAccountsMockMvc.perform(post("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountingStandardIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartOfAccountsRepository.findAll().size();
        // set the field null
        chartOfAccounts.setAccountingStandard(null);

        // Create the ChartOfAccounts, which fails.
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(chartOfAccounts);

        restChartOfAccountsMockMvc.perform(post("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBaseCurrencyIsoIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartOfAccountsRepository.findAll().size();
        // set the field null
        chartOfAccounts.setBaseCurrencyIso(null);

        // Create the ChartOfAccounts, which fails.
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(chartOfAccounts);

        restChartOfAccountsMockMvc.perform(post("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chartOfAccountsRepository.findAll().size();
        // set the field null
        chartOfAccounts.setLegalEntityId(null);

        // Create the ChartOfAccounts, which fails.
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(chartOfAccounts);

        restChartOfAccountsMockMvc.perform(post("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChartOfAccounts() throws Exception {
        // Initialize the database
        chartOfAccountsRepository.saveAndFlush(chartOfAccounts);

        // Get all the chartOfAccountsList
        restChartOfAccountsMockMvc.perform(get("/api/chart-of-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chartOfAccounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].accountingStandard").value(hasItem(DEFAULT_ACCOUNTING_STANDARD.toString())))
            .andExpect(jsonPath("$.[*].baseCurrencyIso").value(hasItem(DEFAULT_BASE_CURRENCY_ISO.toString())))
            .andExpect(jsonPath("$.[*].isMain").value(hasItem(DEFAULT_IS_MAIN.booleanValue())))
            .andExpect(jsonPath("$.[*].legalEntityId").value(hasItem(DEFAULT_LEGAL_ENTITY_ID.toString())));
    }

    @Test
    @Transactional
    public void getChartOfAccounts() throws Exception {
        // Initialize the database
        chartOfAccountsRepository.saveAndFlush(chartOfAccounts);

        // Get the chartOfAccounts
        restChartOfAccountsMockMvc.perform(get("/api/chart-of-accounts/{id}", chartOfAccounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chartOfAccounts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.accountingStandard").value(DEFAULT_ACCOUNTING_STANDARD.toString()))
            .andExpect(jsonPath("$.baseCurrencyIso").value(DEFAULT_BASE_CURRENCY_ISO.toString()))
            .andExpect(jsonPath("$.isMain").value(DEFAULT_IS_MAIN.booleanValue()))
            .andExpect(jsonPath("$.legalEntityId").value(DEFAULT_LEGAL_ENTITY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChartOfAccounts() throws Exception {
        // Get the chartOfAccounts
        restChartOfAccountsMockMvc.perform(get("/api/chart-of-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChartOfAccounts() throws Exception {
        // Initialize the database
        chartOfAccountsRepository.saveAndFlush(chartOfAccounts);
        int databaseSizeBeforeUpdate = chartOfAccountsRepository.findAll().size();

        // Update the chartOfAccounts
        ChartOfAccounts updatedChartOfAccounts = chartOfAccountsRepository.findOne(chartOfAccounts.getId());
        // Disconnect from session so that the updates on updatedChartOfAccounts are not directly saved in db
        em.detach(updatedChartOfAccounts);
        updatedChartOfAccounts
            .name(UPDATED_NAME)
            .key(UPDATED_KEY)
            .accountingStandard(UPDATED_ACCOUNTING_STANDARD)
            .baseCurrencyIso(UPDATED_BASE_CURRENCY_ISO)
            .isMain(UPDATED_IS_MAIN)
            .legalEntityId(UPDATED_LEGAL_ENTITY_ID);
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(updatedChartOfAccounts);

        restChartOfAccountsMockMvc.perform(put("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isOk());

        // Validate the ChartOfAccounts in the database
        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeUpdate);
        ChartOfAccounts testChartOfAccounts = chartOfAccountsList.get(chartOfAccountsList.size() - 1);
        assertThat(testChartOfAccounts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChartOfAccounts.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testChartOfAccounts.getAccountingStandard()).isEqualTo(UPDATED_ACCOUNTING_STANDARD);
        assertThat(testChartOfAccounts.getBaseCurrencyIso()).isEqualTo(UPDATED_BASE_CURRENCY_ISO);
        assertThat(testChartOfAccounts.isIsMain()).isEqualTo(UPDATED_IS_MAIN);
        assertThat(testChartOfAccounts.getLegalEntityId()).isEqualTo(UPDATED_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingChartOfAccounts() throws Exception {
        int databaseSizeBeforeUpdate = chartOfAccountsRepository.findAll().size();

        // Create the ChartOfAccounts
        ChartOfAccountsDTO chartOfAccountsDTO = chartOfAccountsMapper.toDto(chartOfAccounts);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChartOfAccountsMockMvc.perform(put("/api/chart-of-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chartOfAccountsDTO)))
            .andExpect(status().isCreated());

        // Validate the ChartOfAccounts in the database
        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChartOfAccounts() throws Exception {
        // Initialize the database
        chartOfAccountsRepository.saveAndFlush(chartOfAccounts);
        int databaseSizeBeforeDelete = chartOfAccountsRepository.findAll().size();

        // Get the chartOfAccounts
        restChartOfAccountsMockMvc.perform(delete("/api/chart-of-accounts/{id}", chartOfAccounts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChartOfAccounts> chartOfAccountsList = chartOfAccountsRepository.findAll();
        assertThat(chartOfAccountsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChartOfAccounts.class);
        ChartOfAccounts chartOfAccounts1 = new ChartOfAccounts();
        chartOfAccounts1.setId(1L);
        ChartOfAccounts chartOfAccounts2 = new ChartOfAccounts();
        chartOfAccounts2.setId(chartOfAccounts1.getId());
        assertThat(chartOfAccounts1).isEqualTo(chartOfAccounts2);
        chartOfAccounts2.setId(2L);
        assertThat(chartOfAccounts1).isNotEqualTo(chartOfAccounts2);
        chartOfAccounts1.setId(null);
        assertThat(chartOfAccounts1).isNotEqualTo(chartOfAccounts2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChartOfAccountsDTO.class);
        ChartOfAccountsDTO chartOfAccountsDTO1 = new ChartOfAccountsDTO();
        chartOfAccountsDTO1.setId(1L);
        ChartOfAccountsDTO chartOfAccountsDTO2 = new ChartOfAccountsDTO();
        assertThat(chartOfAccountsDTO1).isNotEqualTo(chartOfAccountsDTO2);
        chartOfAccountsDTO2.setId(chartOfAccountsDTO1.getId());
        assertThat(chartOfAccountsDTO1).isEqualTo(chartOfAccountsDTO2);
        chartOfAccountsDTO2.setId(2L);
        assertThat(chartOfAccountsDTO1).isNotEqualTo(chartOfAccountsDTO2);
        chartOfAccountsDTO1.setId(null);
        assertThat(chartOfAccountsDTO1).isNotEqualTo(chartOfAccountsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(chartOfAccountsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(chartOfAccountsMapper.fromId(null)).isNull();
    }
}
