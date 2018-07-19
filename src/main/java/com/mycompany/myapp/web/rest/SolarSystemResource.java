package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.SolarSystemService;
import com.mycompany.myapp.service.dto.SolarSystemStateDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SolarSystemDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SolarSystem.
 */
@RestController
@RequestMapping("/api")
public class SolarSystemResource {

    private final Logger log = LoggerFactory.getLogger(SolarSystemResource.class);

    private static final String ENTITY_NAME = "solarSystem";

    private final SolarSystemService solarSystemService;

    public SolarSystemResource(SolarSystemService solarSystemService) {
        this.solarSystemService = solarSystemService;
    }

    /**
     * POST  /solar-systems : Create a new solarSystem.
     *
     * @param solarSystemDTO the solarSystemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solarSystemDTO, or with status 400 (Bad Request) if the solarSystem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solar-systems")
    @Timed
    public ResponseEntity<SolarSystemDTO> createSolarSystem(@RequestBody SolarSystemDTO solarSystemDTO) throws URISyntaxException {
        log.debug("REST request to save SolarSystem : {}", solarSystemDTO);
        if (solarSystemDTO.getId() != null) {
            throw new BadRequestAlertException("A new solarSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolarSystemDTO result = solarSystemService.save(solarSystemDTO);
        return ResponseEntity.created(new URI("/api/solar-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solar-systems : Updates an existing solarSystem.
     *
     * @param solarSystemDTO the solarSystemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solarSystemDTO,
     * or with status 400 (Bad Request) if the solarSystemDTO is not valid,
     * or with status 500 (Internal Server Error) if the solarSystemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solar-systems")
    @Timed
    public ResponseEntity<SolarSystemDTO> updateSolarSystem(@RequestBody SolarSystemDTO solarSystemDTO) throws URISyntaxException {
        log.debug("REST request to update SolarSystem : {}", solarSystemDTO);
        if (solarSystemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolarSystemDTO result = solarSystemService.save(solarSystemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solarSystemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solar-systems : get all the solarSystems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of solarSystems in body
     */
    @GetMapping("/solar-systems")
    @Timed
    public List<SolarSystemDTO> getAllSolarSystems() {
        log.debug("REST request to get all SolarSystems");
        return solarSystemService.findAll();
    }

    /**
     * GET  /solar-systems/:id : get the "id" solarSystem.
     *
     * @param id the id of the solarSystemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solarSystemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/solar-systems/{id}")
    @Timed
    public ResponseEntity<SolarSystemDTO> getSolarSystem(@PathVariable Long id) {
        log.debug("REST request to get SolarSystem : {}", id);
        Optional<SolarSystemDTO> solarSystemDTO = solarSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solarSystemDTO);
    }

    /**
     * DELETE  /solar-systems/:id : delete the "id" solarSystem.
     *
     * @param id the id of the solarSystemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solar-systems/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolarSystem(@PathVariable Long id) {
        log.debug("REST request to delete SolarSystem : {}", id);
        solarSystemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /solar-systems/:id : get the "id" solarSystem.
     *
     * @param id the id of the solarSystemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solarSystemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/solar-systems/{id}/weather")
    @Timed
    public SolarSystemStateDTO getWeather(@PathVariable Long id, int day) {
        log.debug("REST request to get SolarSystem (id= {} ) weather on a particular day : {}", id, day);
        SolarSystemStateDTO solarSystemStateDTO = solarSystemService.calculateWeatherState(id,day);
        return solarSystemStateDTO;
    }
}
