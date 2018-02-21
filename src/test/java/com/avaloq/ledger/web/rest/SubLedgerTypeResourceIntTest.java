package com.avaloq.ledger.web.rest;

import com.avaloq.ledger.LedgerApp;

import com.avaloq.ledger.domain.SubLedgerType;
import com.avaloq.ledger.repository.SubLedgerTypeRepository;
import com.avaloq.ledger.service.dto.SubLedgerTypeDTO;
import com.avaloq.ledger.service.mapper.SubLedgerTypeMapper;
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
 * Test class for the SubLedgerTypeResource REST controller.
 *
 * @see SubLedgerTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LedgerApp.class)
public class SubLedgerTypeResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubLedgerTypeRepository subLedgerTypeRepository;

    @Autowired
    private SubLedgerTypeMapper subLedgerTypeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubLedgerTypeMockMvc;

    private SubLedgerType subLedgerType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubLedgerTypeResource subLedgerTypeResource = new SubLedgerTypeResource(subLedgerTypeRepository, subLedgerTypeMapper);
        this.restSubLedgerTypeMockMvc = MockMvcBuilders.standaloneSetup(subLedgerTypeResource)
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
    public static SubLedgerType createEntity(EntityManager em) {
        SubLedgerType subLedgerType = new SubLedgerType()
            .category(DEFAULT_CATEGORY)
            .name(DEFAULT_NAME);
        return subLedgerType;
    }

    @Before
    public void initTest() {
        subLedgerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubLedgerType() throws Exception {
        int databaseSizeBeforeCreate = subLedgerTypeRepository.findAll().size();

        // Create the SubLedgerType
        SubLedgerTypeDTO subLedgerTypeDTO = subLedgerTypeMapper.toDto(subLedgerType);
        restSubLedgerTypeMockMvc.perform(post("/api/sub-ledger-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subLedgerTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SubLedgerType in the database
        List<SubLedgerType> subLedgerTypeList = subLedgerTypeRepository.findAll();
        assertThat(subLedgerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SubLedgerType testSubLedgerType = subLedgerTypeList.get(subLedgerTypeList.size() - 1);
        assertThat(testSubLedgerType.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSubLedgerType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubLedgerTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subLedgerTypeRepository.findAll().size();

        // Create the SubLedgerType with an existing ID
        subLedgerType.setId(1L);
        SubLedgerTypeDTO subLedgerTypeDTO = subLedgerTypeMapper.toDto(subLedgerType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubLedgerTypeMockMvc.perform(post("/api/sub-ledger-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subLedgerTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubLedgerType in the database
        List<SubLedgerType> subLedgerTypeList = subLedgerTypeRepository.findAll();
        assertThat(subLedgerTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = subLedgerTypeRepository.findAll().size();
        // set the field null
        subLedgerType.setCategory(null);

        // Create the SubLedgerType, which fails.
        SubLedgerTypeDTO subLedgerTypeDTO = subLedgerTypeMapper.toDto(subLedgerType);

        restSubLedgerTypeMockMvc.perform(post("/api/sub-ledger-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subLedgerTypeDTO)))
            .andExpect(status().isBadRequest());

        List<SubLedgerType> subLedgerTypeList = subLedgerTypeRepository.findAll();
        assertThat(subLedgerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubLedgerTypes() throws Exception {
        // Initialize the database
        subLedgerTypeRepository.saveAndFlush(subLedgerType);

        // Get all the subLedgerTypeList
        restSubLedgerTypeMockMvc.perform(get("/api/sub-ledger-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subLedgerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSubLedgerType() throws Exception {
        // Initialize the database
        subLedgerTypeRepository.saveAndFlush(subLedgerType);

        // Get the subLedgerType
        restSubLedgerTypeMockMvc.perform(get("/api/sub-ledger-types/{id}", subLedgerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subLedgerType.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubLedgerType() throws Exception {
        // Get the subLedgerType
        restSubLedgerTypeMockMvc.perform(get("/api/sub-ledger-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubLedgerType() throws Exception {
        // Initialize the database
        subLedgerTypeRepository.saveAndFlush(subLedgerType);
        int databaseSizeBeforeUpdate = subLedgerTypeRepository.findAll().size();

        // Update the subLedgerType
        SubLedgerType updatedSubLedgerType = subLedgerTypeRepository.findOne(subLedgerType.getId());
        // Disconnect from session so that the updates on updatedSubLedgerType are not directly saved in db
        em.detach(updatedSubLedgerType);
        updatedSubLedgerType
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME);
        SubLedgerTypeDTO subLedgerTypeDTO = subLedgerTypeMapper.toDto(updatedSubLedgerType);

        restSubLedgerTypeMockMvc.perform(put("/api/sub-ledger-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subLedgerTypeDTO)))
            .andExpect(status().isOk());

        // Validate the SubLedgerType in the database
        List<SubLedgerType> subLedgerTypeList = subLedgerTypeRepository.findAll();
        assertThat(subLedgerTypeList).hasSize(databaseSizeBeforeUpdate);
        SubLedgerType testSubLedgerType = subLedgerTypeList.get(subLedgerTypeList.size() - 1);
        assertThat(testSubLedgerType.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSubLedgerType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubLedgerType() throws Exception {
        int databaseSizeBeforeUpdate = subLedgerTypeRepository.findAll().size();

        // Create the SubLedgerType
        SubLedgerTypeDTO subLedgerTypeDTO = subLedgerTypeMapper.toDto(subLedgerType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubLedgerTypeMockMvc.perform(put("/api/sub-ledger-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subLedgerTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SubLedgerType in the database
        List<SubLedgerType> subLedgerTypeList = subLedgerTypeRepository.findAll();
        assertThat(subLedgerTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubLedgerType() throws Exception {
        // Initialize the database
        subLedgerTypeRepository.saveAndFlush(subLedgerType);
        int databaseSizeBeforeDelete = subLedgerTypeRepository.findAll().size();

        // Get the subLedgerType
        restSubLedgerTypeMockMvc.perform(delete("/api/sub-ledger-types/{id}", subLedgerType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubLedgerType> subLedgerTypeList = subLedgerTypeRepository.findAll();
        assertThat(subLedgerTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubLedgerType.class);
        SubLedgerType subLedgerType1 = new SubLedgerType();
        subLedgerType1.setId(1L);
        SubLedgerType subLedgerType2 = new SubLedgerType();
        subLedgerType2.setId(subLedgerType1.getId());
        assertThat(subLedgerType1).isEqualTo(subLedgerType2);
        subLedgerType2.setId(2L);
        assertThat(subLedgerType1).isNotEqualTo(subLedgerType2);
        subLedgerType1.setId(null);
        assertThat(subLedgerType1).isNotEqualTo(subLedgerType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubLedgerTypeDTO.class);
        SubLedgerTypeDTO subLedgerTypeDTO1 = new SubLedgerTypeDTO();
        subLedgerTypeDTO1.setId(1L);
        SubLedgerTypeDTO subLedgerTypeDTO2 = new SubLedgerTypeDTO();
        assertThat(subLedgerTypeDTO1).isNotEqualTo(subLedgerTypeDTO2);
        subLedgerTypeDTO2.setId(subLedgerTypeDTO1.getId());
        assertThat(subLedgerTypeDTO1).isEqualTo(subLedgerTypeDTO2);
        subLedgerTypeDTO2.setId(2L);
        assertThat(subLedgerTypeDTO1).isNotEqualTo(subLedgerTypeDTO2);
        subLedgerTypeDTO1.setId(null);
        assertThat(subLedgerTypeDTO1).isNotEqualTo(subLedgerTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subLedgerTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subLedgerTypeMapper.fromId(null)).isNull();
    }
}
