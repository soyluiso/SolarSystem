package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SolarSystemDTO;
import com.mycompany.myapp.service.dto.SolarSystemStateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SolarSystem.
 */
public interface SolarSystemService {

    /**
     * Save a solarSystem.
     *
     * @param solarSystemDTO the entity to save
     * @return the persisted entity
     */
    SolarSystemDTO save(SolarSystemDTO solarSystemDTO);

    /**
     * Get all the solarSystems.
     *
     * @return the list of entities
     */
    List<SolarSystemDTO> findAll();


    /**
     * Get the "id" solarSystem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SolarSystemDTO> findOne(Long id);

    /**
     * Delete the "id" solarSystem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get the state of the solar system on a day
     *
     * @param id the id of the entity
     * @param day the day
     * @return the state entity
     */
    SolarSystemStateDTO calculateWeatherState(Long id, int day);
}
