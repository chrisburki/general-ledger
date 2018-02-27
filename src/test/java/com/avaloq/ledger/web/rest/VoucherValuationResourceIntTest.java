package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.VoucherValuation;
import com.avaloq.ledger.repository.VoucherValuationRepository;
import com.avaloq.ledger.service.VoucherValuationService;
import com.avaloq.ledger.service.dto.VoucherValuationDTO;
import com.avaloq.ledger.service.mapper.VoucherValuationMapper;
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

import com.avaloq.ledger.domain.enumeration.VoucherDateType;
/**
 * Test class for the VoucherValuationResource REST controller.
 *
 * @see VoucherValuationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class VoucherValuationResourceIntTest {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_CURRENCY_ISO = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ISO = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT_BASE_CURRENCY = 1D;
    private static final Double UPDATED_AMOUNT_BASE_CURRENCY = 2D;

    private static final VoucherDateType DEFAULT_DATE_TYPE = VoucherDateType.DONE;
    private static final VoucherDateType UPDATED_DATE_TYPE = VoucherDateType.VALUE;

    private static final Long DEFAULT_GLOBAL_SEQUENCE_NUMBER = 1L;
    private static final Long UPDATED_GLOBAL_SEQUENCE_NUMBER = 2L;

    private static final String DEFAULT_BUSINESS_USE_CASE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_USE_CASE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_KEEPING_ID = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_KEEPING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private VoucherValuationRepository voucherValuationRepository;

    @Autowired
    private VoucherValuationMapper voucherValuationMapper;

    @Autowired
    private VoucherValuationService voucherValuationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoucherValuationMockMvc;

    private VoucherValuation voucherValuation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherValuationResource voucherValuationResource = new VoucherValuationResource(voucherValuationService);
        this.restVoucherValuationMockMvc = MockMvcBuilders.standaloneSetup(voucherValuationResource)
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
    public static VoucherValuation createEntity(EntityManager em) {
        VoucherValuation voucherValuation = new VoucherValuation()
            .amount(DEFAULT_AMOUNT)
            .currencyIso(DEFAULT_CURRENCY_ISO)
            .amountBaseCurrency(DEFAULT_AMOUNT_BASE_CURRENCY)
            .dateType(DEFAULT_DATE_TYPE)
            .globalSequenceNumber(DEFAULT_GLOBAL_SEQUENCE_NUMBER)
            .businessUseCase(DEFAULT_BUSINESS_USE_CASE)
            .positionKeepingId(DEFAULT_POSITION_KEEPING_ID)
            .legalEntityId(DEFAULT_LEGAL_ENTITY_ID);
        return voucherValuation;
    }

    @Before
    public void initTest() {
        voucherValuation = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucherValuation() throws Exception {
        int databaseSizeBeforeCreate = voucherValuationRepository.findAll().size();

        // Create the VoucherValuation
        VoucherValuationDTO voucherValuationDTO = voucherValuationMapper.toDto(voucherValuation);
        restVoucherValuationMockMvc.perform(post("/api/voucher-valuations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherValuation in the database
        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherValuation testVoucherValuation = voucherValuationList.get(voucherValuationList.size() - 1);
        assertThat(testVoucherValuation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testVoucherValuation.getCurrencyIso()).isEqualTo(DEFAULT_CURRENCY_ISO);
        assertThat(testVoucherValuation.getAmountBaseCurrency()).isEqualTo(DEFAULT_AMOUNT_BASE_CURRENCY);
        assertThat(testVoucherValuation.getDateType()).isEqualTo(DEFAULT_DATE_TYPE);
        assertThat(testVoucherValuation.getGlobalSequenceNumber()).isEqualTo(DEFAULT_GLOBAL_SEQUENCE_NUMBER);
        assertThat(testVoucherValuation.getBusinessUseCase()).isEqualTo(DEFAULT_BUSINESS_USE_CASE);
        assertThat(testVoucherValuation.getPositionKeepingId()).isEqualTo(DEFAULT_POSITION_KEEPING_ID);
        assertThat(testVoucherValuation.getLegalEntityId()).isEqualTo(DEFAULT_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createVoucherValuationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherValuationRepository.findAll().size();

        // Create the VoucherValuation with an existing ID
        voucherValuation.setId(1L);
        VoucherValuationDTO voucherValuationDTO = voucherValuationMapper.toDto(voucherValuation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherValuationMockMvc.perform(post("/api/voucher-valuations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherValuation in the database
        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherValuationRepository.findAll().size();
        // set the field null
        voucherValuation.setAmount(null);

        // Create the VoucherValuation, which fails.
        VoucherValuationDTO voucherValuationDTO = voucherValuationMapper.toDto(voucherValuation);

        restVoucherValuationMockMvc.perform(post("/api/voucher-valuations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherValuationRepository.findAll().size();
        // set the field null
        voucherValuation.setDateType(null);

        // Create the VoucherValuation, which fails.
        VoucherValuationDTO voucherValuationDTO = voucherValuationMapper.toDto(voucherValuation);

        restVoucherValuationMockMvc.perform(post("/api/voucher-valuations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGlobalSequenceNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherValuationRepository.findAll().size();
        // set the field null
        voucherValuation.setGlobalSequenceNumber(null);

        // Create the VoucherValuation, which fails.
        VoucherValuationDTO voucherValuationDTO = voucherValuationMapper.toDto(voucherValuation);

        restVoucherValuationMockMvc.perform(post("/api/voucher-valuations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherValuationRepository.findAll().size();
        // set the field null
        voucherValuation.setLegalEntityId(null);

        // Create the VoucherValuation, which fails.
        VoucherValuationDTO voucherValuationDTO = voucherValuationMapper.toDto(voucherValuation);

        restVoucherValuationMockMvc.perform(post("/api/voucher-valuations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoucherValuations() throws Exception {
        // Initialize the database
        voucherValuationRepository.saveAndFlush(voucherValuation);

        // Get all the voucherValuationList
        restVoucherValuationMockMvc.perform(get("/api/voucher-valuations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherValuation.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyIso").value(hasItem(DEFAULT_CURRENCY_ISO.toString())))
            .andExpect(jsonPath("$.[*].amountBaseCurrency").value(hasItem(DEFAULT_AMOUNT_BASE_CURRENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].dateType").value(hasItem(DEFAULT_DATE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].globalSequenceNumber").value(hasItem(DEFAULT_GLOBAL_SEQUENCE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].businessUseCase").value(hasItem(DEFAULT_BUSINESS_USE_CASE.toString())))
            .andExpect(jsonPath("$.[*].positionKeepingId").value(hasItem(DEFAULT_POSITION_KEEPING_ID.toString())))
            .andExpect(jsonPath("$.[*].legalEntityId").value(hasItem(DEFAULT_LEGAL_ENTITY_ID.toString())));
    }

    @Test
    @Transactional
    public void getVoucherValuation() throws Exception {
        // Initialize the database
        voucherValuationRepository.saveAndFlush(voucherValuation);

        // Get the voucherValuation
        restVoucherValuationMockMvc.perform(get("/api/voucher-valuations/{id}", voucherValuation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucherValuation.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currencyIso").value(DEFAULT_CURRENCY_ISO.toString()))
            .andExpect(jsonPath("$.amountBaseCurrency").value(DEFAULT_AMOUNT_BASE_CURRENCY.doubleValue()))
            .andExpect(jsonPath("$.dateType").value(DEFAULT_DATE_TYPE.toString()))
            .andExpect(jsonPath("$.globalSequenceNumber").value(DEFAULT_GLOBAL_SEQUENCE_NUMBER.intValue()))
            .andExpect(jsonPath("$.businessUseCase").value(DEFAULT_BUSINESS_USE_CASE.toString()))
            .andExpect(jsonPath("$.positionKeepingId").value(DEFAULT_POSITION_KEEPING_ID.toString()))
            .andExpect(jsonPath("$.legalEntityId").value(DEFAULT_LEGAL_ENTITY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucherValuation() throws Exception {
        // Get the voucherValuation
        restVoucherValuationMockMvc.perform(get("/api/voucher-valuations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucherValuation() throws Exception {
        // Initialize the database
        voucherValuationRepository.saveAndFlush(voucherValuation);
        int databaseSizeBeforeUpdate = voucherValuationRepository.findAll().size();

        // Update the voucherValuation
        VoucherValuation updatedVoucherValuation = voucherValuationRepository.findOne(voucherValuation.getId());
        // Disconnect from session so that the updates on updatedVoucherValuation are not directly saved in db
        em.detach(updatedVoucherValuation);
        updatedVoucherValuation
            .amount(UPDATED_AMOUNT)
            .currencyIso(UPDATED_CURRENCY_ISO)
            .amountBaseCurrency(UPDATED_AMOUNT_BASE_CURRENCY)
            .dateType(UPDATED_DATE_TYPE)
            .globalSequenceNumber(UPDATED_GLOBAL_SEQUENCE_NUMBER)
            .businessUseCase(UPDATED_BUSINESS_USE_CASE)
            .positionKeepingId(UPDATED_POSITION_KEEPING_ID)
            .legalEntityId(UPDATED_LEGAL_ENTITY_ID);
        VoucherValuationDTO voucherValuationDTO = voucherValuationMapper.toDto(updatedVoucherValuation);

        restVoucherValuationMockMvc.perform(put("/api/voucher-valuations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationDTO)))
            .andExpect(status().isOk());

        // Validate the VoucherValuation in the database
        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeUpdate);
        VoucherValuation testVoucherValuation = voucherValuationList.get(voucherValuationList.size() - 1);
        assertThat(testVoucherValuation.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testVoucherValuation.getCurrencyIso()).isEqualTo(UPDATED_CURRENCY_ISO);
        assertThat(testVoucherValuation.getAmountBaseCurrency()).isEqualTo(UPDATED_AMOUNT_BASE_CURRENCY);
        assertThat(testVoucherValuation.getDateType()).isEqualTo(UPDATED_DATE_TYPE);
        assertThat(testVoucherValuation.getGlobalSequenceNumber()).isEqualTo(UPDATED_GLOBAL_SEQUENCE_NUMBER);
        assertThat(testVoucherValuation.getBusinessUseCase()).isEqualTo(UPDATED_BUSINESS_USE_CASE);
        assertThat(testVoucherValuation.getPositionKeepingId()).isEqualTo(UPDATED_POSITION_KEEPING_ID);
        assertThat(testVoucherValuation.getLegalEntityId()).isEqualTo(UPDATED_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucherValuation() throws Exception {
        int databaseSizeBeforeUpdate = voucherValuationRepository.findAll().size();

        // Create the VoucherValuation
        VoucherValuationDTO voucherValuationDTO = voucherValuationMapper.toDto(voucherValuation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVoucherValuationMockMvc.perform(put("/api/voucher-valuations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherValuation in the database
        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVoucherValuation() throws Exception {
        // Initialize the database
        voucherValuationRepository.saveAndFlush(voucherValuation);
        int databaseSizeBeforeDelete = voucherValuationRepository.findAll().size();

        // Get the voucherValuation
        restVoucherValuationMockMvc.perform(delete("/api/voucher-valuations/{id}", voucherValuation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoucherValuation> voucherValuationList = voucherValuationRepository.findAll();
        assertThat(voucherValuationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherValuation.class);
        VoucherValuation voucherValuation1 = new VoucherValuation();
        voucherValuation1.setId(1L);
        VoucherValuation voucherValuation2 = new VoucherValuation();
        voucherValuation2.setId(voucherValuation1.getId());
        assertThat(voucherValuation1).isEqualTo(voucherValuation2);
        voucherValuation2.setId(2L);
        assertThat(voucherValuation1).isNotEqualTo(voucherValuation2);
        voucherValuation1.setId(null);
        assertThat(voucherValuation1).isNotEqualTo(voucherValuation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherValuationDTO.class);
        VoucherValuationDTO voucherValuationDTO1 = new VoucherValuationDTO();
        voucherValuationDTO1.setId(1L);
        VoucherValuationDTO voucherValuationDTO2 = new VoucherValuationDTO();
        assertThat(voucherValuationDTO1).isNotEqualTo(voucherValuationDTO2);
        voucherValuationDTO2.setId(voucherValuationDTO1.getId());
        assertThat(voucherValuationDTO1).isEqualTo(voucherValuationDTO2);
        voucherValuationDTO2.setId(2L);
        assertThat(voucherValuationDTO1).isNotEqualTo(voucherValuationDTO2);
        voucherValuationDTO1.setId(null);
        assertThat(voucherValuationDTO1).isNotEqualTo(voucherValuationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherValuationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherValuationMapper.fromId(null)).isNull();
    }
}
