package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.VoucherBooking;
import com.avaloq.ledger.repository.VoucherBookingRepository;
import com.avaloq.ledger.service.VoucherBookingService;
import com.avaloq.ledger.service.dto.VoucherBookingDTO;
import com.avaloq.ledger.service.mapper.VoucherBookingMapper;
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
 * Test class for the VoucherBookingResource REST controller.
 *
 * @see VoucherBookingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class VoucherBookingResourceIntTest {

    private static final LocalDate DEFAULT_DONE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DONE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_CURRENCY_ISO = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ISO = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT_BASE_CURRENCY = 1D;
    private static final Double UPDATED_AMOUNT_BASE_CURRENCY = 2D;

    private static final String DEFAULT_BOOKING_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_BOOKING_TEXT = "BBBBBBBBBB";

    private static final Long DEFAULT_GLOBAL_SEQUENCE_NUMBER = 1L;
    private static final Long UPDATED_GLOBAL_SEQUENCE_NUMBER = 2L;

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_USE_CASE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_USE_CASE = "BBBBBBBBBB";

    private static final String DEFAULT_BOOKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_BOOKING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_KEEPING_ID = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_KEEPING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private VoucherBookingRepository voucherBookingRepository;

    @Autowired
    private VoucherBookingMapper voucherBookingMapper;

    @Autowired
    private VoucherBookingService voucherBookingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoucherBookingMockMvc;

    private VoucherBooking voucherBooking;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherBookingResource voucherBookingResource = new VoucherBookingResource(voucherBookingService);
        this.restVoucherBookingMockMvc = MockMvcBuilders.standaloneSetup(voucherBookingResource)
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
    public static VoucherBooking createEntity(EntityManager em) {
        VoucherBooking voucherBooking = new VoucherBooking()
            .doneDate(DEFAULT_DONE_DATE)
            .valueDate(DEFAULT_VALUE_DATE)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .quantity(DEFAULT_QUANTITY)
            .amount(DEFAULT_AMOUNT)
            .currencyIso(DEFAULT_CURRENCY_ISO)
            .amountBaseCurrency(DEFAULT_AMOUNT_BASE_CURRENCY)
            .bookingText(DEFAULT_BOOKING_TEXT)
            .globalSequenceNumber(DEFAULT_GLOBAL_SEQUENCE_NUMBER)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .eventId(DEFAULT_EVENT_ID)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .businessUseCase(DEFAULT_BUSINESS_USE_CASE)
            .bookingId(DEFAULT_BOOKING_ID)
            .positionKeepingId(DEFAULT_POSITION_KEEPING_ID)
            .legalEntityId(DEFAULT_LEGAL_ENTITY_ID);
        return voucherBooking;
    }

    @Before
    public void initTest() {
        voucherBooking = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucherBooking() throws Exception {
        int databaseSizeBeforeCreate = voucherBookingRepository.findAll().size();

        // Create the VoucherBooking
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);
        restVoucherBookingMockMvc.perform(post("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherBooking in the database
        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherBooking testVoucherBooking = voucherBookingList.get(voucherBookingList.size() - 1);
        assertThat(testVoucherBooking.getDoneDate()).isEqualTo(DEFAULT_DONE_DATE);
        assertThat(testVoucherBooking.getValueDate()).isEqualTo(DEFAULT_VALUE_DATE);
        assertThat(testVoucherBooking.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testVoucherBooking.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testVoucherBooking.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testVoucherBooking.getCurrencyIso()).isEqualTo(DEFAULT_CURRENCY_ISO);
        assertThat(testVoucherBooking.getAmountBaseCurrency()).isEqualTo(DEFAULT_AMOUNT_BASE_CURRENCY);
        assertThat(testVoucherBooking.getBookingText()).isEqualTo(DEFAULT_BOOKING_TEXT);
        assertThat(testVoucherBooking.getGlobalSequenceNumber()).isEqualTo(DEFAULT_GLOBAL_SEQUENCE_NUMBER);
        assertThat(testVoucherBooking.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testVoucherBooking.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testVoucherBooking.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testVoucherBooking.getBusinessUseCase()).isEqualTo(DEFAULT_BUSINESS_USE_CASE);
        assertThat(testVoucherBooking.getBookingId()).isEqualTo(DEFAULT_BOOKING_ID);
        assertThat(testVoucherBooking.getPositionKeepingId()).isEqualTo(DEFAULT_POSITION_KEEPING_ID);
        assertThat(testVoucherBooking.getLegalEntityId()).isEqualTo(DEFAULT_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createVoucherBookingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherBookingRepository.findAll().size();

        // Create the VoucherBooking with an existing ID
        voucherBooking.setId(1L);
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherBookingMockMvc.perform(post("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherBooking in the database
        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDoneDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherBookingRepository.findAll().size();
        // set the field null
        voucherBooking.setDoneDate(null);

        // Create the VoucherBooking, which fails.
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);

        restVoucherBookingMockMvc.perform(post("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherBookingRepository.findAll().size();
        // set the field null
        voucherBooking.setAmount(null);

        // Create the VoucherBooking, which fails.
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);

        restVoucherBookingMockMvc.perform(post("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyIsoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherBookingRepository.findAll().size();
        // set the field null
        voucherBooking.setCurrencyIso(null);

        // Create the VoucherBooking, which fails.
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);

        restVoucherBookingMockMvc.perform(post("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGlobalSequenceNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherBookingRepository.findAll().size();
        // set the field null
        voucherBooking.setGlobalSequenceNumber(null);

        // Create the VoucherBooking, which fails.
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);

        restVoucherBookingMockMvc.perform(post("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBookingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherBookingRepository.findAll().size();
        // set the field null
        voucherBooking.setBookingId(null);

        // Create the VoucherBooking, which fails.
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);

        restVoucherBookingMockMvc.perform(post("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherBookingRepository.findAll().size();
        // set the field null
        voucherBooking.setLegalEntityId(null);

        // Create the VoucherBooking, which fails.
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);

        restVoucherBookingMockMvc.perform(post("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoucherBookings() throws Exception {
        // Initialize the database
        voucherBookingRepository.saveAndFlush(voucherBooking);

        // Get all the voucherBookingList
        restVoucherBookingMockMvc.perform(get("/api/voucher-bookings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherBooking.getId().intValue())))
            .andExpect(jsonPath("$.[*].doneDate").value(hasItem(DEFAULT_DONE_DATE.toString())))
            .andExpect(jsonPath("$.[*].valueDate").value(hasItem(DEFAULT_VALUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyIso").value(hasItem(DEFAULT_CURRENCY_ISO.toString())))
            .andExpect(jsonPath("$.[*].amountBaseCurrency").value(hasItem(DEFAULT_AMOUNT_BASE_CURRENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].bookingText").value(hasItem(DEFAULT_BOOKING_TEXT.toString())))
            .andExpect(jsonPath("$.[*].globalSequenceNumber").value(hasItem(DEFAULT_GLOBAL_SEQUENCE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].businessUseCase").value(hasItem(DEFAULT_BUSINESS_USE_CASE.toString())))
            .andExpect(jsonPath("$.[*].bookingId").value(hasItem(DEFAULT_BOOKING_ID.toString())))
            .andExpect(jsonPath("$.[*].positionKeepingId").value(hasItem(DEFAULT_POSITION_KEEPING_ID.toString())))
            .andExpect(jsonPath("$.[*].legalEntityId").value(hasItem(DEFAULT_LEGAL_ENTITY_ID.toString())));
    }

    @Test
    @Transactional
    public void getVoucherBooking() throws Exception {
        // Initialize the database
        voucherBookingRepository.saveAndFlush(voucherBooking);

        // Get the voucherBooking
        restVoucherBookingMockMvc.perform(get("/api/voucher-bookings/{id}", voucherBooking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucherBooking.getId().intValue()))
            .andExpect(jsonPath("$.doneDate").value(DEFAULT_DONE_DATE.toString()))
            .andExpect(jsonPath("$.valueDate").value(DEFAULT_VALUE_DATE.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currencyIso").value(DEFAULT_CURRENCY_ISO.toString()))
            .andExpect(jsonPath("$.amountBaseCurrency").value(DEFAULT_AMOUNT_BASE_CURRENCY.doubleValue()))
            .andExpect(jsonPath("$.bookingText").value(DEFAULT_BOOKING_TEXT.toString()))
            .andExpect(jsonPath("$.globalSequenceNumber").value(DEFAULT_GLOBAL_SEQUENCE_NUMBER.intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.businessUseCase").value(DEFAULT_BUSINESS_USE_CASE.toString()))
            .andExpect(jsonPath("$.bookingId").value(DEFAULT_BOOKING_ID.toString()))
            .andExpect(jsonPath("$.positionKeepingId").value(DEFAULT_POSITION_KEEPING_ID.toString()))
            .andExpect(jsonPath("$.legalEntityId").value(DEFAULT_LEGAL_ENTITY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucherBooking() throws Exception {
        // Get the voucherBooking
        restVoucherBookingMockMvc.perform(get("/api/voucher-bookings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucherBooking() throws Exception {
        // Initialize the database
        voucherBookingRepository.saveAndFlush(voucherBooking);
        int databaseSizeBeforeUpdate = voucherBookingRepository.findAll().size();

        // Update the voucherBooking
        VoucherBooking updatedVoucherBooking = voucherBookingRepository.findOne(voucherBooking.getId());
        // Disconnect from session so that the updates on updatedVoucherBooking are not directly saved in db
        em.detach(updatedVoucherBooking);
        updatedVoucherBooking
            .doneDate(UPDATED_DONE_DATE)
            .valueDate(UPDATED_VALUE_DATE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .quantity(UPDATED_QUANTITY)
            .amount(UPDATED_AMOUNT)
            .currencyIso(UPDATED_CURRENCY_ISO)
            .amountBaseCurrency(UPDATED_AMOUNT_BASE_CURRENCY)
            .bookingText(UPDATED_BOOKING_TEXT)
            .globalSequenceNumber(UPDATED_GLOBAL_SEQUENCE_NUMBER)
            .transactionId(UPDATED_TRANSACTION_ID)
            .eventId(UPDATED_EVENT_ID)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .businessUseCase(UPDATED_BUSINESS_USE_CASE)
            .bookingId(UPDATED_BOOKING_ID)
            .positionKeepingId(UPDATED_POSITION_KEEPING_ID)
            .legalEntityId(UPDATED_LEGAL_ENTITY_ID);
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(updatedVoucherBooking);

        restVoucherBookingMockMvc.perform(put("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isOk());

        // Validate the VoucherBooking in the database
        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeUpdate);
        VoucherBooking testVoucherBooking = voucherBookingList.get(voucherBookingList.size() - 1);
        assertThat(testVoucherBooking.getDoneDate()).isEqualTo(UPDATED_DONE_DATE);
        assertThat(testVoucherBooking.getValueDate()).isEqualTo(UPDATED_VALUE_DATE);
        assertThat(testVoucherBooking.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testVoucherBooking.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testVoucherBooking.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testVoucherBooking.getCurrencyIso()).isEqualTo(UPDATED_CURRENCY_ISO);
        assertThat(testVoucherBooking.getAmountBaseCurrency()).isEqualTo(UPDATED_AMOUNT_BASE_CURRENCY);
        assertThat(testVoucherBooking.getBookingText()).isEqualTo(UPDATED_BOOKING_TEXT);
        assertThat(testVoucherBooking.getGlobalSequenceNumber()).isEqualTo(UPDATED_GLOBAL_SEQUENCE_NUMBER);
        assertThat(testVoucherBooking.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testVoucherBooking.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testVoucherBooking.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testVoucherBooking.getBusinessUseCase()).isEqualTo(UPDATED_BUSINESS_USE_CASE);
        assertThat(testVoucherBooking.getBookingId()).isEqualTo(UPDATED_BOOKING_ID);
        assertThat(testVoucherBooking.getPositionKeepingId()).isEqualTo(UPDATED_POSITION_KEEPING_ID);
        assertThat(testVoucherBooking.getLegalEntityId()).isEqualTo(UPDATED_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucherBooking() throws Exception {
        int databaseSizeBeforeUpdate = voucherBookingRepository.findAll().size();

        // Create the VoucherBooking
        VoucherBookingDTO voucherBookingDTO = voucherBookingMapper.toDto(voucherBooking);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVoucherBookingMockMvc.perform(put("/api/voucher-bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherBookingDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherBooking in the database
        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVoucherBooking() throws Exception {
        // Initialize the database
        voucherBookingRepository.saveAndFlush(voucherBooking);
        int databaseSizeBeforeDelete = voucherBookingRepository.findAll().size();

        // Get the voucherBooking
        restVoucherBookingMockMvc.perform(delete("/api/voucher-bookings/{id}", voucherBooking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoucherBooking> voucherBookingList = voucherBookingRepository.findAll();
        assertThat(voucherBookingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherBooking.class);
        VoucherBooking voucherBooking1 = new VoucherBooking();
        voucherBooking1.setId(1L);
        VoucherBooking voucherBooking2 = new VoucherBooking();
        voucherBooking2.setId(voucherBooking1.getId());
        assertThat(voucherBooking1).isEqualTo(voucherBooking2);
        voucherBooking2.setId(2L);
        assertThat(voucherBooking1).isNotEqualTo(voucherBooking2);
        voucherBooking1.setId(null);
        assertThat(voucherBooking1).isNotEqualTo(voucherBooking2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherBookingDTO.class);
        VoucherBookingDTO voucherBookingDTO1 = new VoucherBookingDTO();
        voucherBookingDTO1.setId(1L);
        VoucherBookingDTO voucherBookingDTO2 = new VoucherBookingDTO();
        assertThat(voucherBookingDTO1).isNotEqualTo(voucherBookingDTO2);
        voucherBookingDTO2.setId(voucherBookingDTO1.getId());
        assertThat(voucherBookingDTO1).isEqualTo(voucherBookingDTO2);
        voucherBookingDTO2.setId(2L);
        assertThat(voucherBookingDTO1).isNotEqualTo(voucherBookingDTO2);
        voucherBookingDTO1.setId(null);
        assertThat(voucherBookingDTO1).isNotEqualTo(voucherBookingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherBookingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherBookingMapper.fromId(null)).isNull();
    }
}
