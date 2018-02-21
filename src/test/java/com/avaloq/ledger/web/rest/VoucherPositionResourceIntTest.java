package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.VoucherPosition;
import com.avaloq.ledger.repository.VoucherPositionRepository;
import com.avaloq.ledger.service.VoucherPositionService;
import com.avaloq.ledger.service.dto.VoucherPositionDTO;
import com.avaloq.ledger.service.mapper.VoucherPositionMapper;
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

/**
 * Test class for the VoucherPositionResource REST controller.
 *
 * @see VoucherPositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class VoucherPositionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_ISO = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ISO = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_ID = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION_KEEPING_ID = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_KEEPING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ENTITY_ID = "BBBBBBBBBB";

    @Autowired
    private VoucherPositionRepository voucherPositionRepository;

    @Autowired
    private VoucherPositionMapper voucherPositionMapper;

    @Autowired
    private VoucherPositionService voucherPositionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoucherPositionMockMvc;

    private VoucherPosition voucherPosition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherPositionResource voucherPositionResource = new VoucherPositionResource(voucherPositionService);
        this.restVoucherPositionMockMvc = MockMvcBuilders.standaloneSetup(voucherPositionResource)
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
    public static VoucherPosition createEntity(EntityManager em) {
        VoucherPosition voucherPosition = new VoucherPosition()
            .name(DEFAULT_NAME)
            .key(DEFAULT_KEY)
            .currencyIso(DEFAULT_CURRENCY_ISO)
            .positionId(DEFAULT_POSITION_ID)
            .positionKeepingId(DEFAULT_POSITION_KEEPING_ID)
            .legalEntityId(DEFAULT_LEGAL_ENTITY_ID);
        return voucherPosition;
    }

    @Before
    public void initTest() {
        voucherPosition = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucherPosition() throws Exception {
        int databaseSizeBeforeCreate = voucherPositionRepository.findAll().size();

        // Create the VoucherPosition
        VoucherPositionDTO voucherPositionDTO = voucherPositionMapper.toDto(voucherPosition);
        restVoucherPositionMockMvc.perform(post("/api/voucher-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherPositionDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherPosition in the database
        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherPosition testVoucherPosition = voucherPositionList.get(voucherPositionList.size() - 1);
        assertThat(testVoucherPosition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVoucherPosition.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testVoucherPosition.getCurrencyIso()).isEqualTo(DEFAULT_CURRENCY_ISO);
        assertThat(testVoucherPosition.getPositionId()).isEqualTo(DEFAULT_POSITION_ID);
        assertThat(testVoucherPosition.getPositionKeepingId()).isEqualTo(DEFAULT_POSITION_KEEPING_ID);
        assertThat(testVoucherPosition.getLegalEntityId()).isEqualTo(DEFAULT_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void createVoucherPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherPositionRepository.findAll().size();

        // Create the VoucherPosition with an existing ID
        voucherPosition.setId(1L);
        VoucherPositionDTO voucherPositionDTO = voucherPositionMapper.toDto(voucherPosition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherPositionMockMvc.perform(post("/api/voucher-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherPositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherPosition in the database
        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherPositionRepository.findAll().size();
        // set the field null
        voucherPosition.setName(null);

        // Create the VoucherPosition, which fails.
        VoucherPositionDTO voucherPositionDTO = voucherPositionMapper.toDto(voucherPosition);

        restVoucherPositionMockMvc.perform(post("/api/voucher-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherPositionDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyIsoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherPositionRepository.findAll().size();
        // set the field null
        voucherPosition.setCurrencyIso(null);

        // Create the VoucherPosition, which fails.
        VoucherPositionDTO voucherPositionDTO = voucherPositionMapper.toDto(voucherPosition);

        restVoucherPositionMockMvc.perform(post("/api/voucher-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherPositionDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherPositionRepository.findAll().size();
        // set the field null
        voucherPosition.setPositionId(null);

        // Create the VoucherPosition, which fails.
        VoucherPositionDTO voucherPositionDTO = voucherPositionMapper.toDto(voucherPosition);

        restVoucherPositionMockMvc.perform(post("/api/voucher-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherPositionDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherPositionRepository.findAll().size();
        // set the field null
        voucherPosition.setLegalEntityId(null);

        // Create the VoucherPosition, which fails.
        VoucherPositionDTO voucherPositionDTO = voucherPositionMapper.toDto(voucherPosition);

        restVoucherPositionMockMvc.perform(post("/api/voucher-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherPositionDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoucherPositions() throws Exception {
        // Initialize the database
        voucherPositionRepository.saveAndFlush(voucherPosition);

        // Get all the voucherPositionList
        restVoucherPositionMockMvc.perform(get("/api/voucher-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].currencyIso").value(hasItem(DEFAULT_CURRENCY_ISO.toString())))
            .andExpect(jsonPath("$.[*].positionId").value(hasItem(DEFAULT_POSITION_ID.toString())))
            .andExpect(jsonPath("$.[*].positionKeepingId").value(hasItem(DEFAULT_POSITION_KEEPING_ID.toString())))
            .andExpect(jsonPath("$.[*].legalEntityId").value(hasItem(DEFAULT_LEGAL_ENTITY_ID.toString())));
    }

    @Test
    @Transactional
    public void getVoucherPosition() throws Exception {
        // Initialize the database
        voucherPositionRepository.saveAndFlush(voucherPosition);

        // Get the voucherPosition
        restVoucherPositionMockMvc.perform(get("/api/voucher-positions/{id}", voucherPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucherPosition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.currencyIso").value(DEFAULT_CURRENCY_ISO.toString()))
            .andExpect(jsonPath("$.positionId").value(DEFAULT_POSITION_ID.toString()))
            .andExpect(jsonPath("$.positionKeepingId").value(DEFAULT_POSITION_KEEPING_ID.toString()))
            .andExpect(jsonPath("$.legalEntityId").value(DEFAULT_LEGAL_ENTITY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucherPosition() throws Exception {
        // Get the voucherPosition
        restVoucherPositionMockMvc.perform(get("/api/voucher-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucherPosition() throws Exception {
        // Initialize the database
        voucherPositionRepository.saveAndFlush(voucherPosition);
        int databaseSizeBeforeUpdate = voucherPositionRepository.findAll().size();

        // Update the voucherPosition
        VoucherPosition updatedVoucherPosition = voucherPositionRepository.findOne(voucherPosition.getId());
        // Disconnect from session so that the updates on updatedVoucherPosition are not directly saved in db
        em.detach(updatedVoucherPosition);
        updatedVoucherPosition
            .name(UPDATED_NAME)
            .key(UPDATED_KEY)
            .currencyIso(UPDATED_CURRENCY_ISO)
            .positionId(UPDATED_POSITION_ID)
            .positionKeepingId(UPDATED_POSITION_KEEPING_ID)
            .legalEntityId(UPDATED_LEGAL_ENTITY_ID);
        VoucherPositionDTO voucherPositionDTO = voucherPositionMapper.toDto(updatedVoucherPosition);

        restVoucherPositionMockMvc.perform(put("/api/voucher-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherPositionDTO)))
            .andExpect(status().isOk());

        // Validate the VoucherPosition in the database
        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeUpdate);
        VoucherPosition testVoucherPosition = voucherPositionList.get(voucherPositionList.size() - 1);
        assertThat(testVoucherPosition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVoucherPosition.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testVoucherPosition.getCurrencyIso()).isEqualTo(UPDATED_CURRENCY_ISO);
        assertThat(testVoucherPosition.getPositionId()).isEqualTo(UPDATED_POSITION_ID);
        assertThat(testVoucherPosition.getPositionKeepingId()).isEqualTo(UPDATED_POSITION_KEEPING_ID);
        assertThat(testVoucherPosition.getLegalEntityId()).isEqualTo(UPDATED_LEGAL_ENTITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucherPosition() throws Exception {
        int databaseSizeBeforeUpdate = voucherPositionRepository.findAll().size();

        // Create the VoucherPosition
        VoucherPositionDTO voucherPositionDTO = voucherPositionMapper.toDto(voucherPosition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVoucherPositionMockMvc.perform(put("/api/voucher-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherPositionDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherPosition in the database
        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVoucherPosition() throws Exception {
        // Initialize the database
        voucherPositionRepository.saveAndFlush(voucherPosition);
        int databaseSizeBeforeDelete = voucherPositionRepository.findAll().size();

        // Get the voucherPosition
        restVoucherPositionMockMvc.perform(delete("/api/voucher-positions/{id}", voucherPosition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoucherPosition> voucherPositionList = voucherPositionRepository.findAll();
        assertThat(voucherPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherPosition.class);
        VoucherPosition voucherPosition1 = new VoucherPosition();
        voucherPosition1.setId(1L);
        VoucherPosition voucherPosition2 = new VoucherPosition();
        voucherPosition2.setId(voucherPosition1.getId());
        assertThat(voucherPosition1).isEqualTo(voucherPosition2);
        voucherPosition2.setId(2L);
        assertThat(voucherPosition1).isNotEqualTo(voucherPosition2);
        voucherPosition1.setId(null);
        assertThat(voucherPosition1).isNotEqualTo(voucherPosition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherPositionDTO.class);
        VoucherPositionDTO voucherPositionDTO1 = new VoucherPositionDTO();
        voucherPositionDTO1.setId(1L);
        VoucherPositionDTO voucherPositionDTO2 = new VoucherPositionDTO();
        assertThat(voucherPositionDTO1).isNotEqualTo(voucherPositionDTO2);
        voucherPositionDTO2.setId(voucherPositionDTO1.getId());
        assertThat(voucherPositionDTO1).isEqualTo(voucherPositionDTO2);
        voucherPositionDTO2.setId(2L);
        assertThat(voucherPositionDTO1).isNotEqualTo(voucherPositionDTO2);
        voucherPositionDTO1.setId(null);
        assertThat(voucherPositionDTO1).isNotEqualTo(voucherPositionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherPositionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherPositionMapper.fromId(null)).isNull();
    }
}
