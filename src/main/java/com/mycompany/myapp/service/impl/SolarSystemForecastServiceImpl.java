package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SolarSystemForecastService;
import com.mycompany.myapp.domain.SolarSystemForecast;
import com.mycompany.myapp.repository.SolarSystemForecastRepository;
import com.mycompany.myapp.service.dto.SolarSystemForecastDTO;
import com.mycompany.myapp.service.mapper.SolarSystemForecastMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing SolarSystemForecast.
 */
@Service
@Transactional
public class SolarSystemForecastServiceImpl implements SolarSystemForecastService {

    private final Logger log = LoggerFactory.getLogger(SolarSystemForecastServiceImpl.class);

    private final SolarSystemForecastRepository solarSystemForecastRepository;

    private final SolarSystemForecastMapper solarSystemForecastMapper;

    public SolarSystemForecastServiceImpl(SolarSystemForecastRepository solarSystemForecastRepository, SolarSystemForecastMapper solarSystemForecastMapper) {
        this.solarSystemForecastRepository = solarSystemForecastRepository;
        this.solarSystemForecastMapper = solarSystemForecastMapper;
    }

    /**
     * Save a solarSystemForecast.
     *
     * @param solarSystemForecastDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SolarSystemForecastDTO save(SolarSystemForecastDTO solarSystemForecastDTO) {
        log.debug("Request to save SolarSystemForecast : {}", solarSystemForecastDTO);
        SolarSystemForecast solarSystemForecast = solarSystemForecastMapper.toEntity(solarSystemForecastDTO);
        solarSystemForecast = solarSystemForecastRepository.save(solarSystemForecast);
        return solarSystemForecastMapper.toDto(solarSystemForecast);
    }

    /**
     * Get all the solarSystemForecasts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SolarSystemForecastDTO> findAll() {
        log.debug("Request to get all SolarSystemForecasts");
        return solarSystemForecastRepository.findAll().stream()
            .map(solarSystemForecastMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get a Forecast
     *
     * @param id Solar System ID
     * @param day day
     * @return the forecast
     */
    @Transactional(readOnly = true)
    public SolarSystemForecast findBySolarSystemAndDay(Long id, int day) {
        return solarSystemForecastRepository.findBySolarSystemAndDay(id, day);
    }

    /**
     * Get one solarSystemForecast by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SolarSystemForecastDTO> findOne(Long id) {
        log.debug("Request to get SolarSystemForecast : {}", id);
        return solarSystemForecastRepository.findById(id)
            .map(solarSystemForecastMapper::toDto);
    }

    /**
     * Delete the solarSystemForecast by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SolarSystemForecast : {}", id);
        solarSystemForecastRepository.deleteById(id);
    }
}
