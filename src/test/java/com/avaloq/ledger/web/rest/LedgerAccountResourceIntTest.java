package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.LedgerAccount;
import com.avaloq.ledger.repository.LedgerAccountRepository;
import com.avaloq.ledger.service.LedgerAccountService;
import com.avaloq.ledger.service.dto.LedgerAccountDTO;
import com.avaloq.ledger.service.mapper.LedgerAccountMapper;
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

import com.avaloq.ledger.domain.enumeration.LedgerAccountType;
/**
 * Test class for the LedgerAccountResource REST controller.
 *
 * @see LedgerAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class LedgerAccountResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final LedgerAccountType DEFAULT_ACCOUNT_TYPE = LedgerAccountType.ASSETS;
    private static final LedgerAccountType UPDATED_ACCOUNT_TYPE = LedgerAccountType.LIABILITIES;

    private static final String DEFAULT_ORDERED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ORDERED_BY = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Boolean DEFAULT_ISLEAF = false;
    private static final Boolean UPDATED_ISLEAF = true;

    private static final String DEFAULT_BALANCE_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_BALANCE_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private LedgerAccountRepository ledgerAccountRepository;

    @Autowired
    private LedgerAccountMapper ledgerAccountMapper;

    @Autowired
    private LedgerAccountService ledgerAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLedgerAccountMockMvc;

    private LedgerAccount ledgerAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LedgerAccountResource ledgerAccountResource = new LedgerAccountResource(ledgerAccountService);
        this.restLedgerAccountMockMvc = MockMvcBuilders.standaloneSetup(ledgerAccountResource)
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
    public static LedgerAccount createEntity(EntityManager em) {
        LedgerAccount ledgerAccount = new LedgerAccount()
            .name(DEFAULT_NAME)
            .key(DEFAULT_KEY)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .orderedBy(DEFAULT_ORDERED_BY)
            .level(DEFAULT_LEVEL)
            .isleaf(DEFAULT_ISLEAF)
            .balanceAccountId(DEFAULT_BALANCE_ACCOUNT_ID)
            .legalEntityId(DEFAULT_LEGAL_ENTITY_ID);
        return ledgerAccount;
    }

    @Before
    public void initTest() {
        ledgerAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createLedgerAccount() throws Exception {
        int databaseSizeBeforeCreate = ledgerAccountRepository.findAll().size();

        // Create the LedgerAccount
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountMapper.toDto(ledgerAccount);
        restLedgerAccountMockMvc.perform(post("/api/ledger-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledgerAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the LedgerAccount in the database
        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeCreate + 1);
        LedgerAccount testLedgerAccount = ledgerAccountList.get(ledgerAccountList.size() - 1);
        assertThat(testLedgerAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLedgerAccount.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testLedgerAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testLedgerAccount.getOrderedBy()).isEqualTo(DEFAULT_ORDERED_BY);
        assertThat(testLedgerAccount.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testLedgerAccount.isIsleaf()).isEqualTo(DEFAULT_ISLEAF);
        assertThat(testLedgerAccount.getBalanceAccountId()).isEqualTo(DEFAULT_BALANCE_ACCOUNT_ID);
        assertThat(testLedgerAccount.getLegalEntityId()).isEqualTo(DEFAULT_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createLedgerAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ledgerAccountRepository.findAll().size();

        // Create the LedgerAccount with an existing ID
        ledgerAccount.setId(1L);
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountMapper.toDto(ledgerAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLedgerAccountMockMvc.perform(post("/api/ledger-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledgerAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LedgerAccount in the database
        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerAccountRepository.findAll().size();
        // set the field null
        ledgerAccount.setName(null);

        // Create the LedgerAccount, which fails.
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountMapper.toDto(ledgerAccount);

        restLedgerAccountMockMvc.perform(post("/api/ledger-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledgerAccountDTO)))
            .andExpect(status().isBadRequest());

        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerAccountRepository.findAll().size();
        // set the field null
        ledgerAccount.setKey(null);

        // Create the LedgerAccount, which fails.
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountMapper.toDto(ledgerAccount);

        restLedgerAccountMockMvc.perform(post("/api/ledger-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledgerAccountDTO)))
            .andExpect(status().isBadRequest());

        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerAccountRepository.findAll().size();
        // set the field null
        ledgerAccount.setAccountType(null);

        // Create the LedgerAccount, which fails.
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountMapper.toDto(ledgerAccount);

        restLedgerAccountMockMvc.perform(post("/api/ledger-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledgerAccountDTO)))
            .andExpect(status().isBadRequest());

        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerAccountRepository.findAll().size();
        // set the field null
        ledgerAccount.setLegalEntityId(null);

        // Create the LedgerAccount, which fails.
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountMapper.toDto(ledgerAccount);

        restLedgerAccountMockMvc.perform(post("/api/ledger-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledgerAccountDTO)))
            .andExpect(status().isBadRequest());

        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLedgerAccounts() throws Exception {
        // Initialize the database
        ledgerAccountRepository.saveAndFlush(ledgerAccount);

        // Get all the ledgerAccountList
        restLedgerAccountMockMvc.perform(get("/api/ledger-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ledgerAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].orderedBy").value(hasItem(DEFAULT_ORDERED_BY.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].isleaf").value(hasItem(DEFAULT_ISLEAF.booleanValue())))
            .andExpect(jsonPath("$.[*].balanceAccountId").value(hasItem(DEFAULT_BALANCE_ACCOUNT_ID.toString())))
            .andExpect(jsonPath("$.[*].legalEntityId").value(hasItem(DEFAULT_LEGAL_ENTITY_ID.toString())));
    }

    @Test
    @Transactional
    public void getLedgerAccount() throws Exception {
        // Initialize the database
        ledgerAccountRepository.saveAndFlush(ledgerAccount);

        // Get the ledgerAccount
        restLedgerAccountMockMvc.perform(get("/api/ledger-accounts/{id}", ledgerAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ledgerAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.orderedBy").value(DEFAULT_ORDERED_BY.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.isleaf").value(DEFAULT_ISLEAF.booleanValue()))
            .andExpect(jsonPath("$.balanceAccountId").value(DEFAULT_BALANCE_ACCOUNT_ID.toString()))
            .andExpect(jsonPath("$.legalEntityId").value(DEFAULT_LEGAL_ENTITY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLedgerAccount() throws Exception {
        // Get the ledgerAccount
        restLedgerAccountMockMvc.perform(get("/api/ledger-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLedgerAccount() throws Exception {
        // Initialize the database
        ledgerAccountRepository.saveAndFlush(ledgerAccount);
        int databaseSizeBeforeUpdate = ledgerAccountRepository.findAll().size();

        // Update the ledgerAccount
        LedgerAccount updatedLedgerAccount = ledgerAccountRepository.findOne(ledgerAccount.getId());
        // Disconnect from session so that the updates on updatedLedgerAccount are not directly saved in db
        em.detach(updatedLedgerAccount);
        updatedLedgerAccount
            .name(UPDATED_NAME)
            .key(UPDATED_KEY)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .orderedBy(UPDATED_ORDERED_BY)
            .level(UPDATED_LEVEL)
            .isleaf(UPDATED_ISLEAF)
            .balanceAccountId(UPDATED_BALANCE_ACCOUNT_ID)
            .legalEntityId(UPDATED_LEGAL_ENTITY_ID);
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountMapper.toDto(updatedLedgerAccount);

        restLedgerAccountMockMvc.perform(put("/api/ledger-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledgerAccountDTO)))
            .andExpect(status().isOk());

        // Validate the LedgerAccount in the database
        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeUpdate);
        LedgerAccount testLedgerAccount = ledgerAccountList.get(ledgerAccountList.size() - 1);
        assertThat(testLedgerAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLedgerAccount.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testLedgerAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testLedgerAccount.getOrderedBy()).isEqualTo(UPDATED_ORDERED_BY);
        assertThat(testLedgerAccount.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testLedgerAccount.isIsleaf()).isEqualTo(UPDATED_ISLEAF);
        assertThat(testLedgerAccount.getBalanceAccountId()).isEqualTo(UPDATED_BALANCE_ACCOUNT_ID);
        assertThat(testLedgerAccount.getLegalEntityId()).isEqualTo(UPDATED_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingLedgerAccount() throws Exception {
        int databaseSizeBeforeUpdate = ledgerAccountRepository.findAll().size();

        // Create the LedgerAccount
        LedgerAccountDTO ledgerAccountDTO = ledgerAccountMapper.toDto(ledgerAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLedgerAccountMockMvc.perform(put("/api/ledger-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledgerAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the LedgerAccount in the database
        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLedgerAccount() throws Exception {
        // Initialize the database
        ledgerAccountRepository.saveAndFlush(ledgerAccount);
        int databaseSizeBeforeDelete = ledgerAccountRepository.findAll().size();

        // Get the ledgerAccount
        restLedgerAccountMockMvc.perform(delete("/api/ledger-accounts/{id}", ledgerAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LedgerAccount> ledgerAccountList = ledgerAccountRepository.findAll();
        assertThat(ledgerAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LedgerAccount.class);
        LedgerAccount ledgerAccount1 = new LedgerAccount();
        ledgerAccount1.setId(1L);
        LedgerAccount ledgerAccount2 = new LedgerAccount();
        ledgerAccount2.setId(ledgerAccount1.getId());
        assertThat(ledgerAccount1).isEqualTo(ledgerAccount2);
        ledgerAccount2.setId(2L);
        assertThat(ledgerAccount1).isNotEqualTo(ledgerAccount2);
        ledgerAccount1.setId(null);
        assertThat(ledgerAccount1).isNotEqualTo(ledgerAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LedgerAccountDTO.class);
        LedgerAccountDTO ledgerAccountDTO1 = new LedgerAccountDTO();
        ledgerAccountDTO1.setId(1L);
        LedgerAccountDTO ledgerAccountDTO2 = new LedgerAccountDTO();
        assertThat(ledgerAccountDTO1).isNotEqualTo(ledgerAccountDTO2);
        ledgerAccountDTO2.setId(ledgerAccountDTO1.getId());
        assertThat(ledgerAccountDTO1).isEqualTo(ledgerAccountDTO2);
        ledgerAccountDTO2.setId(2L);
        assertThat(ledgerAccountDTO1).isNotEqualTo(ledgerAccountDTO2);
        ledgerAccountDTO1.setId(null);
        assertThat(ledgerAccountDTO1).isNotEqualTo(ledgerAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ledgerAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ledgerAccountMapper.fromId(null)).isNull();
    }
}
