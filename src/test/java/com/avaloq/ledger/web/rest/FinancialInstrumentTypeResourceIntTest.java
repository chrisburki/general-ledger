package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.FinancialInstrumentType;
import com.avaloq.ledger.repository.FinancialInstrumentTypeRepository;
import com.avaloq.ledger.service.FinancialInstrumentTypeService;
import com.avaloq.ledger.service.dto.FinancialInstrumentTypeDTO;
import com.avaloq.ledger.service.mapper.FinancialInstrumentTypeMapper;
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
 * Test class for the FinancialInstrumentTypeResource REST controller.
 *
 * @see FinancialInstrumentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class FinancialInstrumentTypeResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FinancialInstrumentTypeRepository financialInstrumentTypeRepository;

    @Autowired
    private FinancialInstrumentTypeMapper financialInstrumentTypeMapper;

    @Autowired
    private FinancialInstrumentTypeService financialInstrumentTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFinancialInstrumentTypeMockMvc;

    private FinancialInstrumentType financialInstrumentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinancialInstrumentTypeResource financialInstrumentTypeResource = new FinancialInstrumentTypeResource(financialInstrumentTypeService);
        this.restFinancialInstrumentTypeMockMvc = MockMvcBuilders.standaloneSetup(financialInstrumentTypeResource)
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
    public static FinancialInstrumentType createEntity(EntityManager em) {
        FinancialInstrumentType financialInstrumentType = new FinancialInstrumentType()
            .category(DEFAULT_CATEGORY)
            .name(DEFAULT_NAME);
        return financialInstrumentType;
    }

    @Before
    public void initTest() {
        financialInstrumentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinancialInstrumentType() throws Exception {
        int databaseSizeBeforeCreate = financialInstrumentTypeRepository.findAll().size();

        // Create the FinancialInstrumentType
        FinancialInstrumentTypeDTO financialInstrumentTypeDTO = financialInstrumentTypeMapper.toDto(financialInstrumentType);
        restFinancialInstrumentTypeMockMvc.perform(post("/api/financial-instrument-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialInstrumentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the FinancialInstrumentType in the database
        List<FinancialInstrumentType> financialInstrumentTypeList = financialInstrumentTypeRepository.findAll();
        assertThat(financialInstrumentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FinancialInstrumentType testFinancialInstrumentType = financialInstrumentTypeList.get(financialInstrumentTypeList.size() - 1);
        assertThat(testFinancialInstrumentType.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testFinancialInstrumentType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFinancialInstrumentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financialInstrumentTypeRepository.findAll().size();

        // Create the FinancialInstrumentType with an existing ID
        financialInstrumentType.setId(1L);
        FinancialInstrumentTypeDTO financialInstrumentTypeDTO = financialInstrumentTypeMapper.toDto(financialInstrumentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinancialInstrumentTypeMockMvc.perform(post("/api/financial-instrument-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialInstrumentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinancialInstrumentType in the database
        List<FinancialInstrumentType> financialInstrumentTypeList = financialInstrumentTypeRepository.findAll();
        assertThat(financialInstrumentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = financialInstrumentTypeRepository.findAll().size();
        // set the field null
        financialInstrumentType.setCategory(null);

        // Create the FinancialInstrumentType, which fails.
        FinancialInstrumentTypeDTO financialInstrumentTypeDTO = financialInstrumentTypeMapper.toDto(financialInstrumentType);

        restFinancialInstrumentTypeMockMvc.perform(post("/api/financial-instrument-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialInstrumentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<FinancialInstrumentType> financialInstrumentTypeList = financialInstrumentTypeRepository.findAll();
        assertThat(financialInstrumentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFinancialInstrumentTypes() throws Exception {
        // Initialize the database
        financialInstrumentTypeRepository.saveAndFlush(financialInstrumentType);

        // Get all the financialInstrumentTypeList
        restFinancialInstrumentTypeMockMvc.perform(get("/api/financial-instrument-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financialInstrumentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFinancialInstrumentType() throws Exception {
        // Initialize the database
        financialInstrumentTypeRepository.saveAndFlush(financialInstrumentType);

        // Get the financialInstrumentType
        restFinancialInstrumentTypeMockMvc.perform(get("/api/financial-instrument-types/{id}", financialInstrumentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financialInstrumentType.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFinancialInstrumentType() throws Exception {
        // Get the financialInstrumentType
        restFinancialInstrumentTypeMockMvc.perform(get("/api/financial-instrument-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinancialInstrumentType() throws Exception {
        // Initialize the database
        financialInstrumentTypeRepository.saveAndFlush(financialInstrumentType);
        int databaseSizeBeforeUpdate = financialInstrumentTypeRepository.findAll().size();

        // Update the financialInstrumentType
        FinancialInstrumentType updatedFinancialInstrumentType = financialInstrumentTypeRepository.findOne(financialInstrumentType.getId());
        // Disconnect from session so that the updates on updatedFinancialInstrumentType are not directly saved in db
        em.detach(updatedFinancialInstrumentType);
        updatedFinancialInstrumentType
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME);
        FinancialInstrumentTypeDTO financialInstrumentTypeDTO = financialInstrumentTypeMapper.toDto(updatedFinancialInstrumentType);

        restFinancialInstrumentTypeMockMvc.perform(put("/api/financial-instrument-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialInstrumentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the FinancialInstrumentType in the database
        List<FinancialInstrumentType> financialInstrumentTypeList = financialInstrumentTypeRepository.findAll();
        assertThat(financialInstrumentTypeList).hasSize(databaseSizeBeforeUpdate);
        FinancialInstrumentType testFinancialInstrumentType = financialInstrumentTypeList.get(financialInstrumentTypeList.size() - 1);
        assertThat(testFinancialInstrumentType.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testFinancialInstrumentType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFinancialInstrumentType() throws Exception {
        int databaseSizeBeforeUpdate = financialInstrumentTypeRepository.findAll().size();

        // Create the FinancialInstrumentType
        FinancialInstrumentTypeDTO financialInstrumentTypeDTO = financialInstrumentTypeMapper.toDto(financialInstrumentType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFinancialInstrumentTypeMockMvc.perform(put("/api/financial-instrument-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialInstrumentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the FinancialInstrumentType in the database
        List<FinancialInstrumentType> financialInstrumentTypeList = financialInstrumentTypeRepository.findAll();
        assertThat(financialInstrumentTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFinancialInstrumentType() throws Exception {
        // Initialize the database
        financialInstrumentTypeRepository.saveAndFlush(financialInstrumentType);
        int databaseSizeBeforeDelete = financialInstrumentTypeRepository.findAll().size();

        // Get the financialInstrumentType
        restFinancialInstrumentTypeMockMvc.perform(delete("/api/financial-instrument-types/{id}", financialInstrumentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FinancialInstrumentType> financialInstrumentTypeList = financialInstrumentTypeRepository.findAll();
        assertThat(financialInstrumentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialInstrumentType.class);
        FinancialInstrumentType financialInstrumentType1 = new FinancialInstrumentType();
        financialInstrumentType1.setId(1L);
        FinancialInstrumentType financialInstrumentType2 = new FinancialInstrumentType();
        financialInstrumentType2.setId(financialInstrumentType1.getId());
        assertThat(financialInstrumentType1).isEqualTo(financialInstrumentType2);
        financialInstrumentType2.setId(2L);
        assertThat(financialInstrumentType1).isNotEqualTo(financialInstrumentType2);
        financialInstrumentType1.setId(null);
        assertThat(financialInstrumentType1).isNotEqualTo(financialInstrumentType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialInstrumentTypeDTO.class);
        FinancialInstrumentTypeDTO financialInstrumentTypeDTO1 = new FinancialInstrumentTypeDTO();
        financialInstrumentTypeDTO1.setId(1L);
        FinancialInstrumentTypeDTO financialInstrumentTypeDTO2 = new FinancialInstrumentTypeDTO();
        assertThat(financialInstrumentTypeDTO1).isNotEqualTo(financialInstrumentTypeDTO2);
        financialInstrumentTypeDTO2.setId(financialInstrumentTypeDTO1.getId());
        assertThat(financialInstrumentTypeDTO1).isEqualTo(financialInstrumentTypeDTO2);
        financialInstrumentTypeDTO2.setId(2L);
        assertThat(financialInstrumentTypeDTO1).isNotEqualTo(financialInstrumentTypeDTO2);
        financialInstrumentTypeDTO1.setId(null);
        assertThat(financialInstrumentTypeDTO1).isNotEqualTo(financialInstrumentTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(financialInstrumentTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(financialInstrumentTypeMapper.fromId(null)).isNull();
    }
}
