package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SolarSystemForecastDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SolarSystemForecast.
 */
public interface SolarSystemForecastService {

    /**
     * Save a solarSystemForecast.
     *
     * @param solarSystemForecastDTO the entity to save
     * @return the persisted entity
     */
    SolarSystemForecastDTO save(SolarSystemForecastDTO solarSystemForecastDTO);

    /**
     * Get all the solarSystemForecasts.
     *
     * @return the list of entities
     */
    List<SolarSystemForecastDTO> findAll();


    /**
     * Get the "id" solarSystemForecast.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SolarSystemForecastDTO> findOne(Long id);

    /**
     * Delete the "id" solarSystemForecast.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
