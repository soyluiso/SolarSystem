package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Planet;
import com.mycompany.myapp.domain.enumeration.WeatherState;
import com.mycompany.myapp.service.SolarSystemService;
import com.mycompany.myapp.domain.SolarSystem;
import com.mycompany.myapp.repository.SolarSystemRepository;
import com.mycompany.myapp.service.dto.SolarSystemDTO;
import com.mycompany.myapp.service.dto.SolarSystemStateDTO;
import com.mycompany.myapp.service.mapper.SolarSystemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
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
     * Get the weather state of the solar system.
     *
     * @param id the id of the entity
     * @return the solar system state
     */
    @Transactional(readOnly = true)
    public SolarSystemStateDTO calculateWeatherState(Long id, int day) {

        SolarSystem solarSystem = solarSystemRepository.find(id);

        Set<Planet> planets = solarSystem.getPlanets();

        WeatherState weatherState = calculateState(day,planets);

        return new SolarSystemStateDTO(day,weatherState);
    }

    private WeatherState calculateState(int day, Set<Planet> planets) {
        if (planets.size() == 3) {
            List<Planet> list = new ArrayList(planets);

            Planet planet1 = list.get(0);
            Planet planet2 = list.get(1);
            Planet planet3 = list.get(2);

            BigDecimal[] pointInDay1 = planet1.getPointInDay(day);
            BigDecimal[] pointInDay2 = planet2.getPointInDay(day);
            BigDecimal[] pointInDay3 = planet3.getPointInDay(day);

            BigDecimal area = area(pointInDay1[0],pointInDay1[1],pointInDay2[0],pointInDay2[1],pointInDay3[0],pointInDay3[1]);
            boolean isInside = isInside(pointInDay1[0],pointInDay1[1],pointInDay2[0],pointInDay2[1],pointInDay3[0],pointInDay3[1],BigDecimal.ZERO,BigDecimal.ZERO);

            if (area.compareTo(BigDecimal.ZERO) != 0 && isInside) {
                return WeatherState.RAIN;
            }
            else if (area.compareTo(BigDecimal.ZERO) == 0 && isInside) {
                return WeatherState.DROUGHT;
            } else if (area.compareTo(BigDecimal.ZERO) == 0 && !isInside) {
                return WeatherState.OPTIMUM;
            } else {
                return WeatherState.NORMAL;
            }
        }

        return WeatherState.NORMAL;
    }

    /* A utility function to calculate area of triangle
   formed by (x1, y1) (x2, y2) and (x3, y3) */
    static BigDecimal area(BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2,
                           BigDecimal x3, BigDecimal y3)
    {
        return (x1.multiply(y2.subtract(y3)).add(x2.multiply(y3.subtract(y1))).add(x3.multiply(y1.subtract(y2)))).divide(BigDecimal.valueOf(2)).abs();
    }

    /* A function to check whether point P(x, y) lies
   inside the triangle formed by A(x1, y1),
   B(x2, y2) and C(x3, y3) */
    static boolean isInside(BigDecimal x1, BigDecimal y1, BigDecimal x2,
                            BigDecimal y2, BigDecimal x3, BigDecimal y3, BigDecimal x, BigDecimal y)
    {
        /* Calculate area of triangle ABC */
        BigDecimal A = area (x1, y1, x2, y2, x3, y3);

        /* Calculate area of triangle PBC */
        BigDecimal A1 = area (x, y, x2, y2, x3, y3);

        /* Calculate area of triangle PAC */
        BigDecimal A2 = area (x1, y1, x, y, x3, y3);

        /* Calculate area of triangle PAB */
        BigDecimal A3 = area (x1, y1, x2, y2, x, y);

        /* Check if sum of A1, A2 and A3 is same as A */
        return (A.compareTo(A1.add(A2).add(A3)) == 0);
    }

}
