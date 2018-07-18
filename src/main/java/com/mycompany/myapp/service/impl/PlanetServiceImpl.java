package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PlanetService;
import com.mycompany.myapp.domain.Planet;
import com.mycompany.myapp.repository.PlanetRepository;
import com.mycompany.myapp.service.dto.PlanetDTO;
import com.mycompany.myapp.service.mapper.PlanetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Planet.
 */
@Service
@Transactional
public class PlanetServiceImpl implements PlanetService {

    private final Logger log = LoggerFactory.getLogger(PlanetServiceImpl.class);

    private final PlanetRepository planetRepository;

    private final PlanetMapper planetMapper;

    public PlanetServiceImpl(PlanetRepository planetRepository, PlanetMapper planetMapper) {
        this.planetRepository = planetRepository;
        this.planetMapper = planetMapper;
    }

    /**
     * Save a planet.
     *
     * @param planetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PlanetDTO save(PlanetDTO planetDTO) {
        log.debug("Request to save Planet : {}", planetDTO);
        Planet planet = planetMapper.toEntity(planetDTO);
        planet = planetRepository.save(planet);
        return planetMapper.toDto(planet);
    }

    /**
     * Get all the planets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlanetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Planets");
        return planetRepository.findAll(pageable)
            .map(planetMapper::toDto);
    }


    /**
     * Get one planet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlanetDTO> findOne(Long id) {
        log.debug("Request to get Planet : {}", id);
        return planetRepository.findById(id)
            .map(planetMapper::toDto);
    }

    /**
     * Delete the planet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Planet : {}", id);
        planetRepository.deleteById(id);
    }

    /**
     * Get the point of a Planet on a given day on R2
     *
     * @param id the id of the entity
     * @param day the day
     * @return a pair of values on R2
     */
    public double[] getPointInDay(Long id, int day) {
        Planet planet = planetRepository.getOne(id);

        int degrees = planet.getPlanetVelocity().intValue() * day;

        double x = planet.getPlanetRadius() * Math.sin(Math.toRadians(degrees));

        double y = planet.getPlanetRadius() * Math.cos(Math.toRadians(degrees));

        return (new double[]{x, y});
    }
}
