package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.BalanceSheetItem;
import com.avaloq.ledger.repository.BalanceSheetItemRepository;
import com.avaloq.ledger.service.BalanceSheetItemService;
import com.avaloq.ledger.service.dto.BalanceSheetItemDTO;
import com.avaloq.ledger.service.mapper.BalanceSheetItemMapper;
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
 * Test class for the BalanceSheetItemResource REST controller.
 *
 * @see BalanceSheetItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class BalanceSheetItemResourceIntTest {

    private static final LocalDate DEFAULT_BALANCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BALANCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_DELTA_AMOUNT_DEBIT = 1D;
    private static final Double UPDATED_DELTA_AMOUNT_DEBIT = 2D;

    private static final Double DEFAULT_DELTA_AMOUNT_CREDIT = 1D;
    private static final Double UPDATED_DELTA_AMOUNT_CREDIT = 2D;

    private static final String DEFAULT_CURRENCY_ISO = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ISO = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT_CURRENCY = 1D;
    private static final Double UPDATED_AMOUNT_CURRENCY = 2D;

    private static final Double DEFAULT_DELTA_AMOUNT_DEBIT_CURRENCY = 1D;
    private static final Double UPDATED_DELTA_AMOUNT_DEBIT_CURRENCY = 2D;

    private static final Double DEFAULT_DELTA_AMOUNT_CREDIT_CURRENCY = 1D;
    private static final Double UPDATED_DELTA_AMOUNT_CREDIT_CURRENCY = 2D;

    private static final Boolean DEFAULT_IS_FINAL = false;
    private static final Boolean UPDATED_IS_FINAL = true;

    private static final String DEFAULT_LEGAL_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private BalanceSheetItemRepository balanceSheetItemRepository;

    @Autowired
    private BalanceSheetItemMapper balanceSheetItemMapper;

    @Autowired
    private BalanceSheetItemService balanceSheetItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBalanceSheetItemMockMvc;

    private BalanceSheetItem balanceSheetItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BalanceSheetItemResource balanceSheetItemResource = new BalanceSheetItemResource(balanceSheetItemService);
        this.restBalanceSheetItemMockMvc = MockMvcBuilders.standaloneSetup(balanceSheetItemResource)
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
    public static BalanceSheetItem createEntity(EntityManager em) {
        BalanceSheetItem balanceSheetItem = new BalanceSheetItem()
            .balanceDate(DEFAULT_BALANCE_DATE)
            .amount(DEFAULT_AMOUNT)
            .deltaAmountDebit(DEFAULT_DELTA_AMOUNT_DEBIT)
            .deltaAmountCredit(DEFAULT_DELTA_AMOUNT_CREDIT)
            .currencyIso(DEFAULT_CURRENCY_ISO)
            .amountCurrency(DEFAULT_AMOUNT_CURRENCY)
            .deltaAmountDebitCurrency(DEFAULT_DELTA_AMOUNT_DEBIT_CURRENCY)
            .deltaAmountCreditCurrency(DEFAULT_DELTA_AMOUNT_CREDIT_CURRENCY)
            .isFinal(DEFAULT_IS_FINAL)
            .legalEntityId(DEFAULT_LEGAL_ENTITY_ID);
        return balanceSheetItem;
    }

    @Before
    public void initTest() {
        balanceSheetItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createBalanceSheetItem() throws Exception {
        int databaseSizeBeforeCreate = balanceSheetItemRepository.findAll().size();

        // Create the BalanceSheetItem
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(balanceSheetItem);
        restBalanceSheetItemMockMvc.perform(post("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isCreated());

        // Validate the BalanceSheetItem in the database
        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeCreate + 1);
        BalanceSheetItem testBalanceSheetItem = balanceSheetItemList.get(balanceSheetItemList.size() - 1);
        assertThat(testBalanceSheetItem.getBalanceDate()).isEqualTo(DEFAULT_BALANCE_DATE);
        assertThat(testBalanceSheetItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBalanceSheetItem.getDeltaAmountDebit()).isEqualTo(DEFAULT_DELTA_AMOUNT_DEBIT);
        assertThat(testBalanceSheetItem.getDeltaAmountCredit()).isEqualTo(DEFAULT_DELTA_AMOUNT_CREDIT);
        assertThat(testBalanceSheetItem.getCurrencyIso()).isEqualTo(DEFAULT_CURRENCY_ISO);
        assertThat(testBalanceSheetItem.getAmountCurrency()).isEqualTo(DEFAULT_AMOUNT_CURRENCY);
        assertThat(testBalanceSheetItem.getDeltaAmountDebitCurrency()).isEqualTo(DEFAULT_DELTA_AMOUNT_DEBIT_CURRENCY);
        assertThat(testBalanceSheetItem.getDeltaAmountCreditCurrency()).isEqualTo(DEFAULT_DELTA_AMOUNT_CREDIT_CURRENCY);
        assertThat(testBalanceSheetItem.isIsFinal()).isEqualTo(DEFAULT_IS_FINAL);
        assertThat(testBalanceSheetItem.getLegalEntityId()).isEqualTo(DEFAULT_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createBalanceSheetItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = balanceSheetItemRepository.findAll().size();

        // Create the BalanceSheetItem with an existing ID
        balanceSheetItem.setId(1L);
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(balanceSheetItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalanceSheetItemMockMvc.perform(post("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BalanceSheetItem in the database
        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBalanceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetItemRepository.findAll().size();
        // set the field null
        balanceSheetItem.setBalanceDate(null);

        // Create the BalanceSheetItem, which fails.
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(balanceSheetItem);

        restBalanceSheetItemMockMvc.perform(post("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetItemRepository.findAll().size();
        // set the field null
        balanceSheetItem.setAmount(null);

        // Create the BalanceSheetItem, which fails.
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(balanceSheetItem);

        restBalanceSheetItemMockMvc.perform(post("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeltaAmountDebitIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetItemRepository.findAll().size();
        // set the field null
        balanceSheetItem.setDeltaAmountDebit(null);

        // Create the BalanceSheetItem, which fails.
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(balanceSheetItem);

        restBalanceSheetItemMockMvc.perform(post("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeltaAmountCreditIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetItemRepository.findAll().size();
        // set the field null
        balanceSheetItem.setDeltaAmountCredit(null);

        // Create the BalanceSheetItem, which fails.
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(balanceSheetItem);

        restBalanceSheetItemMockMvc.perform(post("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = balanceSheetItemRepository.findAll().size();
        // set the field null
        balanceSheetItem.setLegalEntityId(null);

        // Create the BalanceSheetItem, which fails.
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(balanceSheetItem);

        restBalanceSheetItemMockMvc.perform(post("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isBadRequest());

        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBalanceSheetItems() throws Exception {
        // Initialize the database
        balanceSheetItemRepository.saveAndFlush(balanceSheetItem);

        // Get all the balanceSheetItemList
        restBalanceSheetItemMockMvc.perform(get("/api/balance-sheet-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balanceSheetItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].balanceDate").value(hasItem(DEFAULT_BALANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].deltaAmountDebit").value(hasItem(DEFAULT_DELTA_AMOUNT_DEBIT.doubleValue())))
            .andExpect(jsonPath("$.[*].deltaAmountCredit").value(hasItem(DEFAULT_DELTA_AMOUNT_CREDIT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyIso").value(hasItem(DEFAULT_CURRENCY_ISO.toString())))
            .andExpect(jsonPath("$.[*].amountCurrency").value(hasItem(DEFAULT_AMOUNT_CURRENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].deltaAmountDebitCurrency").value(hasItem(DEFAULT_DELTA_AMOUNT_DEBIT_CURRENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].deltaAmountCreditCurrency").value(hasItem(DEFAULT_DELTA_AMOUNT_CREDIT_CURRENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].isFinal").value(hasItem(DEFAULT_IS_FINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].legalEntityId").value(hasItem(DEFAULT_LEGAL_ENTITY_ID.toString())));
    }

    @Test
    @Transactional
    public void getBalanceSheetItem() throws Exception {
        // Initialize the database
        balanceSheetItemRepository.saveAndFlush(balanceSheetItem);

        // Get the balanceSheetItem
        restBalanceSheetItemMockMvc.perform(get("/api/balance-sheet-items/{id}", balanceSheetItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(balanceSheetItem.getId().intValue()))
            .andExpect(jsonPath("$.balanceDate").value(DEFAULT_BALANCE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.deltaAmountDebit").value(DEFAULT_DELTA_AMOUNT_DEBIT.doubleValue()))
            .andExpect(jsonPath("$.deltaAmountCredit").value(DEFAULT_DELTA_AMOUNT_CREDIT.doubleValue()))
            .andExpect(jsonPath("$.currencyIso").value(DEFAULT_CURRENCY_ISO.toString()))
            .andExpect(jsonPath("$.amountCurrency").value(DEFAULT_AMOUNT_CURRENCY.doubleValue()))
            .andExpect(jsonPath("$.deltaAmountDebitCurrency").value(DEFAULT_DELTA_AMOUNT_DEBIT_CURRENCY.doubleValue()))
            .andExpect(jsonPath("$.deltaAmountCreditCurrency").value(DEFAULT_DELTA_AMOUNT_CREDIT_CURRENCY.doubleValue()))
            .andExpect(jsonPath("$.isFinal").value(DEFAULT_IS_FINAL.booleanValue()))
            .andExpect(jsonPath("$.legalEntityId").value(DEFAULT_LEGAL_ENTITY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBalanceSheetItem() throws Exception {
        // Get the balanceSheetItem
        restBalanceSheetItemMockMvc.perform(get("/api/balance-sheet-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBalanceSheetItem() throws Exception {
        // Initialize the database
        balanceSheetItemRepository.saveAndFlush(balanceSheetItem);
        int databaseSizeBeforeUpdate = balanceSheetItemRepository.findAll().size();

        // Update the balanceSheetItem
        BalanceSheetItem updatedBalanceSheetItem = balanceSheetItemRepository.findOne(balanceSheetItem.getId());
        // Disconnect from session so that the updates on updatedBalanceSheetItem are not directly saved in db
        em.detach(updatedBalanceSheetItem);
        updatedBalanceSheetItem
            .balanceDate(UPDATED_BALANCE_DATE)
            .amount(UPDATED_AMOUNT)
            .deltaAmountDebit(UPDATED_DELTA_AMOUNT_DEBIT)
            .deltaAmountCredit(UPDATED_DELTA_AMOUNT_CREDIT)
            .currencyIso(UPDATED_CURRENCY_ISO)
            .amountCurrency(UPDATED_AMOUNT_CURRENCY)
            .deltaAmountDebitCurrency(UPDATED_DELTA_AMOUNT_DEBIT_CURRENCY)
            .deltaAmountCreditCurrency(UPDATED_DELTA_AMOUNT_CREDIT_CURRENCY)
            .isFinal(UPDATED_IS_FINAL)
            .legalEntityId(UPDATED_LEGAL_ENTITY_ID);
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(updatedBalanceSheetItem);

        restBalanceSheetItemMockMvc.perform(put("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isOk());

        // Validate the BalanceSheetItem in the database
        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeUpdate);
        BalanceSheetItem testBalanceSheetItem = balanceSheetItemList.get(balanceSheetItemList.size() - 1);
        assertThat(testBalanceSheetItem.getBalanceDate()).isEqualTo(UPDATED_BALANCE_DATE);
        assertThat(testBalanceSheetItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBalanceSheetItem.getDeltaAmountDebit()).isEqualTo(UPDATED_DELTA_AMOUNT_DEBIT);
        assertThat(testBalanceSheetItem.getDeltaAmountCredit()).isEqualTo(UPDATED_DELTA_AMOUNT_CREDIT);
        assertThat(testBalanceSheetItem.getCurrencyIso()).isEqualTo(UPDATED_CURRENCY_ISO);
        assertThat(testBalanceSheetItem.getAmountCurrency()).isEqualTo(UPDATED_AMOUNT_CURRENCY);
        assertThat(testBalanceSheetItem.getDeltaAmountDebitCurrency()).isEqualTo(UPDATED_DELTA_AMOUNT_DEBIT_CURRENCY);
        assertThat(testBalanceSheetItem.getDeltaAmountCreditCurrency()).isEqualTo(UPDATED_DELTA_AMOUNT_CREDIT_CURRENCY);
        assertThat(testBalanceSheetItem.isIsFinal()).isEqualTo(UPDATED_IS_FINAL);
        assertThat(testBalanceSheetItem.getLegalEntityId()).isEqualTo(UPDATED_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBalanceSheetItem() throws Exception {
        int databaseSizeBeforeUpdate = balanceSheetItemRepository.findAll().size();

        // Create the BalanceSheetItem
        BalanceSheetItemDTO balanceSheetItemDTO = balanceSheetItemMapper.toDto(balanceSheetItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBalanceSheetItemMockMvc.perform(put("/api/balance-sheet-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceSheetItemDTO)))
            .andExpect(status().isCreated());

        // Validate the BalanceSheetItem in the database
        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBalanceSheetItem() throws Exception {
        // Initialize the database
        balanceSheetItemRepository.saveAndFlush(balanceSheetItem);
        int databaseSizeBeforeDelete = balanceSheetItemRepository.findAll().size();

        // Get the balanceSheetItem
        restBalanceSheetItemMockMvc.perform(delete("/api/balance-sheet-items/{id}", balanceSheetItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BalanceSheetItem> balanceSheetItemList = balanceSheetItemRepository.findAll();
        assertThat(balanceSheetItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceSheetItem.class);
        BalanceSheetItem balanceSheetItem1 = new BalanceSheetItem();
        balanceSheetItem1.setId(1L);
        BalanceSheetItem balanceSheetItem2 = new BalanceSheetItem();
        balanceSheetItem2.setId(balanceSheetItem1.getId());
        assertThat(balanceSheetItem1).isEqualTo(balanceSheetItem2);
        balanceSheetItem2.setId(2L);
        assertThat(balanceSheetItem1).isNotEqualTo(balanceSheetItem2);
        balanceSheetItem1.setId(null);
        assertThat(balanceSheetItem1).isNotEqualTo(balanceSheetItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceSheetItemDTO.class);
        BalanceSheetItemDTO balanceSheetItemDTO1 = new BalanceSheetItemDTO();
        balanceSheetItemDTO1.setId(1L);
        BalanceSheetItemDTO balanceSheetItemDTO2 = new BalanceSheetItemDTO();
        assertThat(balanceSheetItemDTO1).isNotEqualTo(balanceSheetItemDTO2);
        balanceSheetItemDTO2.setId(balanceSheetItemDTO1.getId());
        assertThat(balanceSheetItemDTO1).isEqualTo(balanceSheetItemDTO2);
        balanceSheetItemDTO2.setId(2L);
        assertThat(balanceSheetItemDTO1).isNotEqualTo(balanceSheetItemDTO2);
        balanceSheetItemDTO1.setId(null);
        assertThat(balanceSheetItemDTO1).isNotEqualTo(balanceSheetItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(balanceSheetItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(balanceSheetItemMapper.fromId(null)).isNull();
    }
}
