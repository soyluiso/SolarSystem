package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SolarSystemService;
import com.mycompany.myapp.domain.SolarSystem;
import com.mycompany.myapp.repository.SolarSystemRepository;
import com.mycompany.myapp.service.dto.SolarSystemDTO;
import com.mycompany.myapp.service.mapper.SolarSystemMapper;
import com.mycompany.myapp.service.dto.SolarSystemStateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing SolarSystem.
 */
@Service
@Transactional
public class SolarSystemServiceImpl implements SolarSystemService {

    private final Logger log = LoggerFactory.getLogger(SolarSystemServiceImpl.class);

    private final SolarSystemRepository solarSystemRepository;

    private final SolarSystemMapper solarSystemMapper;

    public SolarSystemServiceImpl(SolarSystemRepository solarSystemRepository, SolarSystemMapper solarSystemMapper) {
        this.solarSystemRepository = solarSystemRepository;
        this.solarSystemMapper = solarSystemMapper;
    }

    /**
     * Save a solarSystem.
     *
     * @param solarSystemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SolarSystemDTO save(SolarSystemDTO solarSystemDTO) {
        log.debug("Request to save SolarSystem : {}", solarSystemDTO);
        SolarSystem solarSystem = solarSystemMapper.toEntity(solarSystemDTO);
        solarSystem = solarSystemRepository.save(solarSystem);
        return solarSystemMapper.toDto(solarSystem);
    }

    /**
     * Get all the solarSystems.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SolarSystemDTO> findAll() {
        log.debug("Request to get all SolarSystems");
        return solarSystemRepository.findAll().stream()
            .map(solarSystemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one solarSystem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SolarSystemDTO> findOne(Long id) {
        log.debug("Request to get SolarSystem : {}", id);
        return solarSystemRepository.findById(id)
            .map(solarSystemMapper::toDto);
    }

    /**
     * Delete the solarSystem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SolarSystem : {}", id);
        solarSystemRepository.deleteById(id);
    }

    /**
     *
     *
     * @param id
     * @return
     */
    public int[] getDryDays(Long id) {
        Optional<SolarSystem> solarSystem = solarSystemRepository.findById(id);



        return null;
    }


    /**
     * Get the weather state of the solar system.
     *
     * @param id the id of the entity
     * @return the solar system state
     */
    @Transactional(readOnly = true)
    public SolarSystemStateDTO calculateWeatherState(Long id, int days) {

        SolarSystemDTO dto = solarSystemRepository.findById(id)
            .map(solarSystemMapper::toDto).get();

        return null;

        //List<Planet> planets = solarSystem.getPlanets();

        //WeatherState weatherState = calculateState(days,planets);

        //return new SolarSystemStateDTO(days,weatherState);
    }

}
