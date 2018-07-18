package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SolarSystemApp;

import com.mycompany.myapp.domain.SolarSystemForecast;
import com.mycompany.myapp.repository.SolarSystemForecastRepository;
import com.mycompany.myapp.service.SolarSystemForecastService;
import com.mycompany.myapp.service.dto.SolarSystemForecastDTO;
import com.mycompany.myapp.service.mapper.SolarSystemForecastMapper;
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
 * Test class for the SolarSystemForecastResource REST controller.
 *
 * @see SolarSystemForecastResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolarSystemApp.class)
public class SolarSystemForecastResourceIntTest {

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;

    private static final String DEFAULT_FORECAST = "AAAAAAAAAA";
    private static final String UPDATED_FORECAST = "BBBBBBBBBB";

    @Autowired
    private SolarSystemForecastRepository solarSystemForecastRepository;


    @Autowired
    private SolarSystemForecastMapper solarSystemForecastMapper;
    

    @Autowired
    private SolarSystemForecastService solarSystemForecastService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolarSystemForecastMockMvc;

    private SolarSystemForecast solarSystemForecast;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolarSystemForecastResource solarSystemForecastResource = new SolarSystemForecastResource(solarSystemForecastService);
        this.restSolarSystemForecastMockMvc = MockMvcBuilders.standaloneSetup(solarSystemForecastResource)
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
    public static SolarSystemForecast createEntity(EntityManager em) {
        SolarSystemForecast solarSystemForecast = new SolarSystemForecast()
            .day(DEFAULT_DAY)
            .forecast(DEFAULT_FORECAST);
        return solarSystemForecast;
    }

    @Before
    public void initTest() {
        solarSystemForecast = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolarSystemForecast() throws Exception {
        int databaseSizeBeforeCreate = solarSystemForecastRepository.findAll().size();

        // Create the SolarSystemForecast
        SolarSystemForecastDTO solarSystemForecastDTO = solarSystemForecastMapper.toDto(solarSystemForecast);
        restSolarSystemForecastMockMvc.perform(post("/api/solar-system-forecasts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solarSystemForecastDTO)))
            .andExpect(status().isCreated());

        // Validate the SolarSystemForecast in the database
        List<SolarSystemForecast> solarSystemForecastList = solarSystemForecastRepository.findAll();
        assertThat(solarSystemForecastList).hasSize(databaseSizeBeforeCreate + 1);
        SolarSystemForecast testSolarSystemForecast = solarSystemForecastList.get(solarSystemForecastList.size() - 1);
        assertThat(testSolarSystemForecast.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testSolarSystemForecast.getForecast()).isEqualTo(DEFAULT_FORECAST);
    }

    @Test
    @Transactional
    public void createSolarSystemForecastWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solarSystemForecastRepository.findAll().size();

        // Create the SolarSystemForecast with an existing ID
        solarSystemForecast.setId(1L);
        SolarSystemForecastDTO solarSystemForecastDTO = solarSystemForecastMapper.toDto(solarSystemForecast);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolarSystemForecastMockMvc.perform(post("/api/solar-system-forecasts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solarSystemForecastDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolarSystemForecast in the database
        List<SolarSystemForecast> solarSystemForecastList = solarSystemForecastRepository.findAll();
        assertThat(solarSystemForecastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSolarSystemForecasts() throws Exception {
        // Initialize the database
        solarSystemForecastRepository.saveAndFlush(solarSystemForecast);

        // Get all the solarSystemForecastList
        restSolarSystemForecastMockMvc.perform(get("/api/solar-system-forecasts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solarSystemForecast.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].forecast").value(hasItem(DEFAULT_FORECAST.toString())));
    }
    

    @Test
    @Transactional
    public void getSolarSystemForecast() throws Exception {
        // Initialize the database
        solarSystemForecastRepository.saveAndFlush(solarSystemForecast);

        // Get the solarSystemForecast
        restSolarSystemForecastMockMvc.perform(get("/api/solar-system-forecasts/{id}", solarSystemForecast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solarSystemForecast.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.forecast").value(DEFAULT_FORECAST.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSolarSystemForecast() throws Exception {
        // Get the solarSystemForecast
        restSolarSystemForecastMockMvc.perform(get("/api/solar-system-forecasts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolarSystemForecast() throws Exception {
        // Initialize the database
        solarSystemForecastRepository.saveAndFlush(solarSystemForecast);

        int databaseSizeBeforeUpdate = solarSystemForecastRepository.findAll().size();

        // Update the solarSystemForecast
        SolarSystemForecast updatedSolarSystemForecast = solarSystemForecastRepository.findById(solarSystemForecast.getId()).get();
        // Disconnect from session so that the updates on updatedSolarSystemForecast are not directly saved in db
        em.detach(updatedSolarSystemForecast);
        updatedSolarSystemForecast
            .day(UPDATED_DAY)
            .forecast(UPDATED_FORECAST);
        SolarSystemForecastDTO solarSystemForecastDTO = solarSystemForecastMapper.toDto(updatedSolarSystemForecast);

        restSolarSystemForecastMockMvc.perform(put("/api/solar-system-forecasts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solarSystemForecastDTO)))
            .andExpect(status().isOk());

        // Validate the SolarSystemForecast in the database
        List<SolarSystemForecast> solarSystemForecastList = solarSystemForecastRepository.findAll();
        assertThat(solarSystemForecastList).hasSize(databaseSizeBeforeUpdate);
        SolarSystemForecast testSolarSystemForecast = solarSystemForecastList.get(solarSystemForecastList.size() - 1);
        assertThat(testSolarSystemForecast.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testSolarSystemForecast.getForecast()).isEqualTo(UPDATED_FORECAST);
    }

    @Test
    @Transactional
    public void updateNonExistingSolarSystemForecast() throws Exception {
        int databaseSizeBeforeUpdate = solarSystemForecastRepository.findAll().size();

        // Create the SolarSystemForecast
        SolarSystemForecastDTO solarSystemForecastDTO = solarSystemForecastMapper.toDto(solarSystemForecast);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolarSystemForecastMockMvc.perform(put("/api/solar-system-forecasts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solarSystemForecastDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolarSystemForecast in the database
        List<SolarSystemForecast> solarSystemForecastList = solarSystemForecastRepository.findAll();
        assertThat(solarSystemForecastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolarSystemForecast() throws Exception {
        // Initialize the database
        solarSystemForecastRepository.saveAndFlush(solarSystemForecast);

        int databaseSizeBeforeDelete = solarSystemForecastRepository.findAll().size();

        // Get the solarSystemForecast
        restSolarSystemForecastMockMvc.perform(delete("/api/solar-system-forecasts/{id}", solarSystemForecast.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SolarSystemForecast> solarSystemForecastList = solarSystemForecastRepository.findAll();
        assertThat(solarSystemForecastList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolarSystemForecast.class);
        SolarSystemForecast solarSystemForecast1 = new SolarSystemForecast();
        solarSystemForecast1.setId(1L);
        SolarSystemForecast solarSystemForecast2 = new SolarSystemForecast();
        solarSystemForecast2.setId(solarSystemForecast1.getId());
        assertThat(solarSystemForecast1).isEqualTo(solarSystemForecast2);
        solarSystemForecast2.setId(2L);
        assertThat(solarSystemForecast1).isNotEqualTo(solarSystemForecast2);
        solarSystemForecast1.setId(null);
        assertThat(solarSystemForecast1).isNotEqualTo(solarSystemForecast2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolarSystemForecastDTO.class);
        SolarSystemForecastDTO solarSystemForecastDTO1 = new SolarSystemForecastDTO();
        solarSystemForecastDTO1.setId(1L);
        SolarSystemForecastDTO solarSystemForecastDTO2 = new SolarSystemForecastDTO();
        assertThat(solarSystemForecastDTO1).isNotEqualTo(solarSystemForecastDTO2);
        solarSystemForecastDTO2.setId(solarSystemForecastDTO1.getId());
        assertThat(solarSystemForecastDTO1).isEqualTo(solarSystemForecastDTO2);
        solarSystemForecastDTO2.setId(2L);
        assertThat(solarSystemForecastDTO1).isNotEqualTo(solarSystemForecastDTO2);
        solarSystemForecastDTO1.setId(null);
        assertThat(solarSystemForecastDTO1).isNotEqualTo(solarSystemForecastDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(solarSystemForecastMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(solarSystemForecastMapper.fromId(null)).isNull();
    }
}
