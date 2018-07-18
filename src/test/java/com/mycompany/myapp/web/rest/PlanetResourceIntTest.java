package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SolarSystemApp;

import com.mycompany.myapp.domain.Planet;
import com.mycompany.myapp.repository.PlanetRepository;
import com.mycompany.myapp.service.PlanetService;
import com.mycompany.myapp.service.dto.PlanetDTO;
import com.mycompany.myapp.service.mapper.PlanetMapper;
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
 * Test class for the PlanetResource REST controller.
 *
 * @see PlanetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolarSystemApp.class)
public class PlanetResourceIntTest {

    private static final String DEFAULT_PLANET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLANET_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PLANET_VELOCITY = 1L;
    private static final Long UPDATED_PLANET_VELOCITY = 2L;

    private static final Long DEFAULT_PLANET_RADIUS = 1L;
    private static final Long UPDATED_PLANET_RADIUS = 2L;

    @Autowired
    private PlanetRepository planetRepository;


    @Autowired
    private PlanetMapper planetMapper;
    

    @Autowired
    private PlanetService planetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanetMockMvc;

    private Planet planet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanetResource planetResource = new PlanetResource(planetService);
        this.restPlanetMockMvc = MockMvcBuilders.standaloneSetup(planetResource)
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
    public static Planet createEntity(EntityManager em) {
        Planet planet = new Planet()
            .planetName(DEFAULT_PLANET_NAME)
            .planetVelocity(DEFAULT_PLANET_VELOCITY)
            .planetRadius(DEFAULT_PLANET_RADIUS);
        return planet;
    }

    @Before
    public void initTest() {
        planet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanet() throws Exception {
        int databaseSizeBeforeCreate = planetRepository.findAll().size();

        // Create the Planet
        PlanetDTO planetDTO = planetMapper.toDto(planet);
        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isCreated());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeCreate + 1);
        Planet testPlanet = planetList.get(planetList.size() - 1);
        assertThat(testPlanet.getPlanetName()).isEqualTo(DEFAULT_PLANET_NAME);
        assertThat(testPlanet.getPlanetVelocity()).isEqualTo(DEFAULT_PLANET_VELOCITY);
        assertThat(testPlanet.getPlanetRadius()).isEqualTo(DEFAULT_PLANET_RADIUS);
    }

    @Test
    @Transactional
    public void createPlanetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planetRepository.findAll().size();

        // Create the Planet with an existing ID
        planet.setId(1L);
        PlanetDTO planetDTO = planetMapper.toDto(planet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlanets() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        // Get all the planetList
        restPlanetMockMvc.perform(get("/api/planets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planet.getId().intValue())))
            .andExpect(jsonPath("$.[*].planetName").value(hasItem(DEFAULT_PLANET_NAME.toString())))
            .andExpect(jsonPath("$.[*].planetVelocity").value(hasItem(DEFAULT_PLANET_VELOCITY.intValue())))
            .andExpect(jsonPath("$.[*].planetRadius").value(hasItem(DEFAULT_PLANET_RADIUS.intValue())));
    }
    

    @Test
    @Transactional
    public void getPlanet() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        // Get the planet
        restPlanetMockMvc.perform(get("/api/planets/{id}", planet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planet.getId().intValue()))
            .andExpect(jsonPath("$.planetName").value(DEFAULT_PLANET_NAME.toString()))
            .andExpect(jsonPath("$.planetVelocity").value(DEFAULT_PLANET_VELOCITY.intValue()))
            .andExpect(jsonPath("$.planetRadius").value(DEFAULT_PLANET_RADIUS.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPlanet() throws Exception {
        // Get the planet
        restPlanetMockMvc.perform(get("/api/planets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanet() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        int databaseSizeBeforeUpdate = planetRepository.findAll().size();

        // Update the planet
        Planet updatedPlanet = planetRepository.findById(planet.getId()).get();
        // Disconnect from session so that the updates on updatedPlanet are not directly saved in db
        em.detach(updatedPlanet);
        updatedPlanet
            .planetName(UPDATED_PLANET_NAME)
            .planetVelocity(UPDATED_PLANET_VELOCITY)
            .planetRadius(UPDATED_PLANET_RADIUS);
        PlanetDTO planetDTO = planetMapper.toDto(updatedPlanet);

        restPlanetMockMvc.perform(put("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isOk());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeUpdate);
        Planet testPlanet = planetList.get(planetList.size() - 1);
        assertThat(testPlanet.getPlanetName()).isEqualTo(UPDATED_PLANET_NAME);
        assertThat(testPlanet.getPlanetVelocity()).isEqualTo(UPDATED_PLANET_VELOCITY);
        assertThat(testPlanet.getPlanetRadius()).isEqualTo(UPDATED_PLANET_RADIUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanet() throws Exception {
        int databaseSizeBeforeUpdate = planetRepository.findAll().size();

        // Create the Planet
        PlanetDTO planetDTO = planetMapper.toDto(planet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlanetMockMvc.perform(put("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanet() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        int databaseSizeBeforeDelete = planetRepository.findAll().size();

        // Get the planet
        restPlanetMockMvc.perform(delete("/api/planets/{id}", planet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Planet.class);
        Planet planet1 = new Planet();
        planet1.setId(1L);
        Planet planet2 = new Planet();
        planet2.setId(planet1.getId());
        assertThat(planet1).isEqualTo(planet2);
        planet2.setId(2L);
        assertThat(planet1).isNotEqualTo(planet2);
        planet1.setId(null);
        assertThat(planet1).isNotEqualTo(planet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanetDTO.class);
        PlanetDTO planetDTO1 = new PlanetDTO();
        planetDTO1.setId(1L);
        PlanetDTO planetDTO2 = new PlanetDTO();
        assertThat(planetDTO1).isNotEqualTo(planetDTO2);
        planetDTO2.setId(planetDTO1.getId());
        assertThat(planetDTO1).isEqualTo(planetDTO2);
        planetDTO2.setId(2L);
        assertThat(planetDTO1).isNotEqualTo(planetDTO2);
        planetDTO1.setId(null);
        assertThat(planetDTO1).isNotEqualTo(planetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(planetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(planetMapper.fromId(null)).isNull();
    }
}
