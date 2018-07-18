package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SolarSystemApp;

import com.mycompany.myapp.domain.SolarSystem;
import com.mycompany.myapp.repository.SolarSystemRepository;
import com.mycompany.myapp.service.SolarSystemService;
import com.mycompany.myapp.service.dto.SolarSystemDTO;
import com.mycompany.myapp.service.mapper.SolarSystemMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SolarSystemResource REST controller.
 *
 * @see SolarSystemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolarSystemApp.class)
public class SolarSystemResourceIntTest {

    private static final String DEFAULT_SYSTEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM_NAME = "BBBBBBBBBB";

    @Autowired
    private SolarSystemRepository solarSystemRepository;


    @Autowired
    private SolarSystemMapper solarSystemMapper;
    

    @Autowired
    private SolarSystemService solarSystemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolarSystemMockMvc;

    private SolarSystem solarSystem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolarSystemResource solarSystemResource = new SolarSystemResource(solarSystemService);
        this.restSolarSystemMockMvc = MockMvcBuilders.standaloneSetup(solarSystemResource)
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
    public static SolarSystem createEntity(EntityManager em) {
        SolarSystem solarSystem = new SolarSystem()
            .systemName(DEFAULT_SYSTEM_NAME);
        return solarSystem;
    }

    @Before
    public void initTest() {
        solarSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolarSystem() throws Exception {
        int databaseSizeBeforeCreate = solarSystemRepository.findAll().size();

        // Create the SolarSystem
        SolarSystemDTO solarSystemDTO = solarSystemMapper.toDto(solarSystem);
        restSolarSystemMockMvc.perform(post("/api/solar-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solarSystemDTO)))
            .andExpect(status().isCreated());

        // Validate the SolarSystem in the database
        List<SolarSystem> solarSystemList = solarSystemRepository.findAll();
        assertThat(solarSystemList).hasSize(databaseSizeBeforeCreate + 1);
        SolarSystem testSolarSystem = solarSystemList.get(solarSystemList.size() - 1);
        assertThat(testSolarSystem.getSystemName()).isEqualTo(DEFAULT_SYSTEM_NAME);
    }

    @Test
    @Transactional
    public void createSolarSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solarSystemRepository.findAll().size();

        // Create the SolarSystem with an existing ID
        solarSystem.setId(1L);
        SolarSystemDTO solarSystemDTO = solarSystemMapper.toDto(solarSystem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolarSystemMockMvc.perform(post("/api/solar-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solarSystemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolarSystem in the database
        List<SolarSystem> solarSystemList = solarSystemRepository.findAll();
        assertThat(solarSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSolarSystems() throws Exception {
        // Initialize the database
        solarSystemRepository.saveAndFlush(solarSystem);

        // Get all the solarSystemList
        restSolarSystemMockMvc.perform(get("/api/solar-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solarSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].systemName").value(hasItem(DEFAULT_SYSTEM_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getSolarSystem() throws Exception {
        // Initialize the database
        solarSystemRepository.saveAndFlush(solarSystem);

        // Get the solarSystem
        restSolarSystemMockMvc.perform(get("/api/solar-systems/{id}", solarSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solarSystem.getId().intValue()))
            .andExpect(jsonPath("$.systemName").value(DEFAULT_SYSTEM_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSolarSystem() throws Exception {
        // Get the solarSystem
        restSolarSystemMockMvc.perform(get("/api/solar-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolarSystem() throws Exception {
        // Initialize the database
        solarSystemRepository.saveAndFlush(solarSystem);

        int databaseSizeBeforeUpdate = solarSystemRepository.findAll().size();

        // Update the solarSystem
        SolarSystem updatedSolarSystem = solarSystemRepository.findById(solarSystem.getId()).get();
        // Disconnect from session so that the updates on updatedSolarSystem are not directly saved in db
        em.detach(updatedSolarSystem);
        updatedSolarSystem
            .systemName(UPDATED_SYSTEM_NAME);
        SolarSystemDTO solarSystemDTO = solarSystemMapper.toDto(updatedSolarSystem);

        restSolarSystemMockMvc.perform(put("/api/solar-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solarSystemDTO)))
            .andExpect(status().isOk());

        // Validate the SolarSystem in the database
        List<SolarSystem> solarSystemList = solarSystemRepository.findAll();
        assertThat(solarSystemList).hasSize(databaseSizeBeforeUpdate);
        SolarSystem testSolarSystem = solarSystemList.get(solarSystemList.size() - 1);
        assertThat(testSolarSystem.getSystemName()).isEqualTo(UPDATED_SYSTEM_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSolarSystem() throws Exception {
        int databaseSizeBeforeUpdate = solarSystemRepository.findAll().size();

        // Create the SolarSystem
        SolarSystemDTO solarSystemDTO = solarSystemMapper.toDto(solarSystem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolarSystemMockMvc.perform(put("/api/solar-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solarSystemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolarSystem in the database
        List<SolarSystem> solarSystemList = solarSystemRepository.findAll();
        assertThat(solarSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolarSystem() throws Exception {
        // Initialize the database
        solarSystemRepository.saveAndFlush(solarSystem);

        int databaseSizeBeforeDelete = solarSystemRepository.findAll().size();

        // Get the solarSystem
        restSolarSystemMockMvc.perform(delete("/api/solar-systems/{id}", solarSystem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SolarSystem> solarSystemList = solarSystemRepository.findAll();
        assertThat(solarSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolarSystem.class);
        SolarSystem solarSystem1 = new SolarSystem();
        solarSystem1.setId(1L);
        SolarSystem solarSystem2 = new SolarSystem();
        solarSystem2.setId(solarSystem1.getId());
        assertThat(solarSystem1).isEqualTo(solarSystem2);
        solarSystem2.setId(2L);
        assertThat(solarSystem1).isNotEqualTo(solarSystem2);
        solarSystem1.setId(null);
        assertThat(solarSystem1).isNotEqualTo(solarSystem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolarSystemDTO.class);
        SolarSystemDTO solarSystemDTO1 = new SolarSystemDTO();
        solarSystemDTO1.setId(1L);
        SolarSystemDTO solarSystemDTO2 = new SolarSystemDTO();
        assertThat(solarSystemDTO1).isNotEqualTo(solarSystemDTO2);
        solarSystemDTO2.setId(solarSystemDTO1.getId());
        assertThat(solarSystemDTO1).isEqualTo(solarSystemDTO2);
        solarSystemDTO2.setId(2L);
        assertThat(solarSystemDTO1).isNotEqualTo(solarSystemDTO2);
        solarSystemDTO1.setId(null);
        assertThat(solarSystemDTO1).isNotEqualTo(solarSystemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(solarSystemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(solarSystemMapper.fromId(null)).isNull();
    }
}
