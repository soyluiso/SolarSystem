package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PlanetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service Interface for managing Planet.
 */
public interface PlanetService {

    /**
     * Save a planet.
     *
     * @param planetDTO the entity to save
     * @return the persisted entity
     */
    PlanetDTO save(PlanetDTO planetDTO);

    /**
     * Get all the planets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PlanetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PlanetDTO> findOne(Long id);

    /**
     * Delete the "id" planet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get point of the planet in a given day
     *
     * @param id the id of the entity
     * @param day the day
     * @return
     */
    BigDecimal[] getPointInDay(Long id, int day);
}
