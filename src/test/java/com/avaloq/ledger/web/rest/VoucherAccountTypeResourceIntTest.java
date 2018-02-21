package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.VoucherAccountType;
import com.avaloq.ledger.repository.VoucherAccountTypeRepository;
import com.avaloq.ledger.service.dto.VoucherAccountTypeDTO;
import com.avaloq.ledger.service.mapper.VoucherAccountTypeMapper;
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
 * Test class for the VoucherAccountTypeResource REST controller.
 *
 * @see VoucherAccountTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class VoucherAccountTypeResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private VoucherAccountTypeRepository voucherAccountTypeRepository;

    @Autowired
    private VoucherAccountTypeMapper voucherAccountTypeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoucherAccountTypeMockMvc;

    private VoucherAccountType voucherAccountType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherAccountTypeResource voucherAccountTypeResource = new VoucherAccountTypeResource(voucherAccountTypeRepository, voucherAccountTypeMapper);
        this.restVoucherAccountTypeMockMvc = MockMvcBuilders.standaloneSetup(voucherAccountTypeResource)
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
    public static VoucherAccountType createEntity(EntityManager em) {
        VoucherAccountType voucherAccountType = new VoucherAccountType()
            .category(DEFAULT_CATEGORY)
            .name(DEFAULT_NAME);
        return voucherAccountType;
    }

    @Before
    public void initTest() {
        voucherAccountType = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucherAccountType() throws Exception {
        int databaseSizeBeforeCreate = voucherAccountTypeRepository.findAll().size();

        // Create the VoucherAccountType
        VoucherAccountTypeDTO voucherAccountTypeDTO = voucherAccountTypeMapper.toDto(voucherAccountType);
        restVoucherAccountTypeMockMvc.perform(post("/api/voucher-account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherAccountTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherAccountType in the database
        List<VoucherAccountType> voucherAccountTypeList = voucherAccountTypeRepository.findAll();
        assertThat(voucherAccountTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherAccountType testVoucherAccountType = voucherAccountTypeList.get(voucherAccountTypeList.size() - 1);
        assertThat(testVoucherAccountType.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testVoucherAccountType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createVoucherAccountTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherAccountTypeRepository.findAll().size();

        // Create the VoucherAccountType with an existing ID
        voucherAccountType.setId(1L);
        VoucherAccountTypeDTO voucherAccountTypeDTO = voucherAccountTypeMapper.toDto(voucherAccountType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherAccountTypeMockMvc.perform(post("/api/voucher-account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherAccountTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherAccountType in the database
        List<VoucherAccountType> voucherAccountTypeList = voucherAccountTypeRepository.findAll();
        assertThat(voucherAccountTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherAccountTypeRepository.findAll().size();
        // set the field null
        voucherAccountType.setCategory(null);

        // Create the VoucherAccountType, which fails.
        VoucherAccountTypeDTO voucherAccountTypeDTO = voucherAccountTypeMapper.toDto(voucherAccountType);

        restVoucherAccountTypeMockMvc.perform(post("/api/voucher-account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherAccountTypeDTO)))
            .andExpect(status().isBadRequest());

        List<VoucherAccountType> voucherAccountTypeList = voucherAccountTypeRepository.findAll();
        assertThat(voucherAccountTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoucherAccountTypes() throws Exception {
        // Initialize the database
        voucherAccountTypeRepository.saveAndFlush(voucherAccountType);

        // Get all the voucherAccountTypeList
        restVoucherAccountTypeMockMvc.perform(get("/api/voucher-account-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherAccountType.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getVoucherAccountType() throws Exception {
        // Initialize the database
        voucherAccountTypeRepository.saveAndFlush(voucherAccountType);

        // Get the voucherAccountType
        restVoucherAccountTypeMockMvc.perform(get("/api/voucher-account-types/{id}", voucherAccountType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucherAccountType.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucherAccountType() throws Exception {
        // Get the voucherAccountType
        restVoucherAccountTypeMockMvc.perform(get("/api/voucher-account-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucherAccountType() throws Exception {
        // Initialize the database
        voucherAccountTypeRepository.saveAndFlush(voucherAccountType);
        int databaseSizeBeforeUpdate = voucherAccountTypeRepository.findAll().size();

        // Update the voucherAccountType
        VoucherAccountType updatedVoucherAccountType = voucherAccountTypeRepository.findOne(voucherAccountType.getId());
        // Disconnect from session so that the updates on updatedVoucherAccountType are not directly saved in db
        em.detach(updatedVoucherAccountType);
        updatedVoucherAccountType
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME);
        VoucherAccountTypeDTO voucherAccountTypeDTO = voucherAccountTypeMapper.toDto(updatedVoucherAccountType);

        restVoucherAccountTypeMockMvc.perform(put("/api/voucher-account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherAccountTypeDTO)))
            .andExpect(status().isOk());

        // Validate the VoucherAccountType in the database
        List<VoucherAccountType> voucherAccountTypeList = voucherAccountTypeRepository.findAll();
        assertThat(voucherAccountTypeList).hasSize(databaseSizeBeforeUpdate);
        VoucherAccountType testVoucherAccountType = voucherAccountTypeList.get(voucherAccountTypeList.size() - 1);
        assertThat(testVoucherAccountType.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testVoucherAccountType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucherAccountType() throws Exception {
        int databaseSizeBeforeUpdate = voucherAccountTypeRepository.findAll().size();

        // Create the VoucherAccountType
        VoucherAccountTypeDTO voucherAccountTypeDTO = voucherAccountTypeMapper.toDto(voucherAccountType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVoucherAccountTypeMockMvc.perform(put("/api/voucher-account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherAccountTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherAccountType in the database
        List<VoucherAccountType> voucherAccountTypeList = voucherAccountTypeRepository.findAll();
        assertThat(voucherAccountTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVoucherAccountType() throws Exception {
        // Initialize the database
        voucherAccountTypeRepository.saveAndFlush(voucherAccountType);
        int databaseSizeBeforeDelete = voucherAccountTypeRepository.findAll().size();

        // Get the voucherAccountType
        restVoucherAccountTypeMockMvc.perform(delete("/api/voucher-account-types/{id}", voucherAccountType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoucherAccountType> voucherAccountTypeList = voucherAccountTypeRepository.findAll();
        assertThat(voucherAccountTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherAccountType.class);
        VoucherAccountType voucherAccountType1 = new VoucherAccountType();
        voucherAccountType1.setId(1L);
        VoucherAccountType voucherAccountType2 = new VoucherAccountType();
        voucherAccountType2.setId(voucherAccountType1.getId());
        assertThat(voucherAccountType1).isEqualTo(voucherAccountType2);
        voucherAccountType2.setId(2L);
        assertThat(voucherAccountType1).isNotEqualTo(voucherAccountType2);
        voucherAccountType1.setId(null);
        assertThat(voucherAccountType1).isNotEqualTo(voucherAccountType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherAccountTypeDTO.class);
        VoucherAccountTypeDTO voucherAccountTypeDTO1 = new VoucherAccountTypeDTO();
        voucherAccountTypeDTO1.setId(1L);
        VoucherAccountTypeDTO voucherAccountTypeDTO2 = new VoucherAccountTypeDTO();
        assertThat(voucherAccountTypeDTO1).isNotEqualTo(voucherAccountTypeDTO2);
        voucherAccountTypeDTO2.setId(voucherAccountTypeDTO1.getId());
        assertThat(voucherAccountTypeDTO1).isEqualTo(voucherAccountTypeDTO2);
        voucherAccountTypeDTO2.setId(2L);
        assertThat(voucherAccountTypeDTO1).isNotEqualTo(voucherAccountTypeDTO2);
        voucherAccountTypeDTO1.setId(null);
        assertThat(voucherAccountTypeDTO1).isNotEqualTo(voucherAccountTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherAccountTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherAccountTypeMapper.fromId(null)).isNull();
    }
}
