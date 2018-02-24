package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.VoucherValuationType;
import com.avaloq.ledger.repository.VoucherValuationTypeRepository;
import com.avaloq.ledger.service.dto.VoucherValuationTypeDTO;
import com.avaloq.ledger.service.mapper.VoucherValuationTypeMapper;
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
 * Test class for the VoucherValuationTypeResource REST controller.
 *
 * @see VoucherValuationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class VoucherValuationTypeResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private VoucherValuationTypeRepository voucherValuationTypeRepository;

    @Autowired
    private VoucherValuationTypeMapper voucherValuationTypeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoucherValuationTypeMockMvc;

    private VoucherValuationType voucherValuationType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherValuationTypeResource voucherValuationTypeResource = new VoucherValuationTypeResource(voucherValuationTypeRepository, voucherValuationTypeMapper);
        this.restVoucherValuationTypeMockMvc = MockMvcBuilders.standaloneSetup(voucherValuationTypeResource)
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
    public static VoucherValuationType createEntity(EntityManager em) {
        VoucherValuationType voucherValuationType = new VoucherValuationType()
            .category(DEFAULT_CATEGORY)
            .name(DEFAULT_NAME);
        return voucherValuationType;
    }

    @Before
    public void initTest() {
        voucherValuationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucherValuationType() throws Exception {
        int databaseSizeBeforeCreate = voucherValuationTypeRepository.findAll().size();

        // Create the VoucherValuationType
        VoucherValuationTypeDTO voucherValuationTypeDTO = voucherValuationTypeMapper.toDto(voucherValuationType);
        restVoucherValuationTypeMockMvc.perform(post("/api/voucher-valuation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherValuationType in the database
        List<VoucherValuationType> voucherValuationTypeList = voucherValuationTypeRepository.findAll();
        assertThat(voucherValuationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherValuationType testVoucherValuationType = voucherValuationTypeList.get(voucherValuationTypeList.size() - 1);
        assertThat(testVoucherValuationType.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testVoucherValuationType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createVoucherValuationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherValuationTypeRepository.findAll().size();

        // Create the VoucherValuationType with an existing ID
        voucherValuationType.setId(1L);
        VoucherValuationTypeDTO voucherValuationTypeDTO = voucherValuationTypeMapper.toDto(voucherValuationType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherValuationTypeMockMvc.perform(post("/api/voucher-valuation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherValuationType in the database
        List<VoucherValuationType> voucherValuationTypeList = voucherValuationTypeRepository.findAll();
        assertThat(voucherValuationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherValuationTypeRepository.findAll().size();
        // set the field null
        voucherValuationType.setCategory(null);

        // Create the VoucherValuationType, which fails.
        VoucherValuationTypeDTO voucherValuationTypeDTO = voucherValuationTypeMapper.toDto(voucherValuationType);

        restVoucherValuationTypeMockMvc.perform(post("/api/voucher-valuation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherValuationType> voucherValuationTypeList = voucherValuationTypeRepository.findAll();
        assertThat(voucherValuationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoucherValuationTypes() throws Exception {
        // Initialize the database
        voucherValuationTypeRepository.saveAndFlush(voucherValuationType);

        // Get all the voucherValuationTypeList
        restVoucherValuationTypeMockMvc.perform(get("/api/voucher-valuation-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherValuationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getVoucherValuationType() throws Exception {
        // Initialize the database
        voucherValuationTypeRepository.saveAndFlush(voucherValuationType);

        // Get the voucherValuationType
        restVoucherValuationTypeMockMvc.perform(get("/api/voucher-valuation-types/{id}", voucherValuationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucherValuationType.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucherValuationType() throws Exception {
        // Get the voucherValuationType
        restVoucherValuationTypeMockMvc.perform(get("/api/voucher-valuation-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucherValuationType() throws Exception {
        // Initialize the database
        voucherValuationTypeRepository.saveAndFlush(voucherValuationType);
        int databaseSizeBeforeUpdate = voucherValuationTypeRepository.findAll().size();

        // Update the voucherValuationType
        VoucherValuationType updatedVoucherValuationType = voucherValuationTypeRepository.findOne(voucherValuationType.getId());
        // Disconnect from session so that the updates on updatedVoucherValuationType are not directly saved in db
        em.detach(updatedVoucherValuationType);
        updatedVoucherValuationType
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME);
        VoucherValuationTypeDTO voucherValuationTypeDTO = voucherValuationTypeMapper.toDto(updatedVoucherValuationType);

        restVoucherValuationTypeMockMvc.perform(put("/api/voucher-valuation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationTypeDTO)))
            .andExpect(status().isOk());

        // Validate the VoucherValuationType in the database
        List<VoucherValuationType> voucherValuationTypeList = voucherValuationTypeRepository.findAll();
        assertThat(voucherValuationTypeList).hasSize(databaseSizeBeforeUpdate);
        VoucherValuationType testVoucherValuationType = voucherValuationTypeList.get(voucherValuationTypeList.size() - 1);
        assertThat(testVoucherValuationType.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testVoucherValuationType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucherValuationType() throws Exception {
        int databaseSizeBeforeUpdate = voucherValuationTypeRepository.findAll().size();

        // Create the VoucherValuationType
        VoucherValuationTypeDTO voucherValuationTypeDTO = voucherValuationTypeMapper.toDto(voucherValuationType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVoucherValuationTypeMockMvc.perform(put("/api/voucher-valuation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherValuationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherValuationType in the database
        List<VoucherValuationType> voucherValuationTypeList = voucherValuationTypeRepository.findAll();
        assertThat(voucherValuationTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVoucherValuationType() throws Exception {
        // Initialize the database
        voucherValuationTypeRepository.saveAndFlush(voucherValuationType);
        int databaseSizeBeforeDelete = voucherValuationTypeRepository.findAll().size();

        // Get the voucherValuationType
        restVoucherValuationTypeMockMvc.perform(delete("/api/voucher-valuation-types/{id}", voucherValuationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoucherValuationType> voucherValuationTypeList = voucherValuationTypeRepository.findAll();
        assertThat(voucherValuationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherValuationType.class);
        VoucherValuationType voucherValuationType1 = new VoucherValuationType();
        voucherValuationType1.setId(1L);
        VoucherValuationType voucherValuationType2 = new VoucherValuationType();
        voucherValuationType2.setId(voucherValuationType1.getId());
        assertThat(voucherValuationType1).isEqualTo(voucherValuationType2);
        voucherValuationType2.setId(2L);
        assertThat(voucherValuationType1).isNotEqualTo(voucherValuationType2);
        voucherValuationType1.setId(null);
        assertThat(voucherValuationType1).isNotEqualTo(voucherValuationType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherValuationTypeDTO.class);
        VoucherValuationTypeDTO voucherValuationTypeDTO1 = new VoucherValuationTypeDTO();
        voucherValuationTypeDTO1.setId(1L);
        VoucherValuationTypeDTO voucherValuationTypeDTO2 = new VoucherValuationTypeDTO();
        assertThat(voucherValuationTypeDTO1).isNotEqualTo(voucherValuationTypeDTO2);
        voucherValuationTypeDTO2.setId(voucherValuationTypeDTO1.getId());
        assertThat(voucherValuationTypeDTO1).isEqualTo(voucherValuationTypeDTO2);
        voucherValuationTypeDTO2.setId(2L);
        assertThat(voucherValuationTypeDTO1).isNotEqualTo(voucherValuationTypeDTO2);
        voucherValuationTypeDTO1.setId(null);
        assertThat(voucherValuationTypeDTO1).isNotEqualTo(voucherValuationTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherValuationTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherValuationTypeMapper.fromId(null)).isNull();
    }
}
