package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.SolarSystemForecastService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SolarSystemForecastDTO;
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
 * REST controller for managing SolarSystemForecast.
 */
@RestController
@RequestMapping("/api")
public class SolarSystemForecastResource {

    private final Logger log = LoggerFactory.getLogger(SolarSystemForecastResource.class);

    private static final String ENTITY_NAME = "solarSystemForecast";

    private final SolarSystemForecastService solarSystemForecastService;

    public SolarSystemForecastResource(SolarSystemForecastService solarSystemForecastService) {
        this.solarSystemForecastService = solarSystemForecastService;
    }

    /**
     * POST  /solar-system-forecasts : Create a new solarSystemForecast.
     *
     * @param solarSystemForecastDTO the solarSystemForecastDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solarSystemForecastDTO, or with status 400 (Bad Request) if the solarSystemForecast has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solar-system-forecasts")
    @Timed
    public ResponseEntity<SolarSystemForecastDTO> createSolarSystemForecast(@RequestBody SolarSystemForecastDTO solarSystemForecastDTO) throws URISyntaxException {
        log.debug("REST request to save SolarSystemForecast : {}", solarSystemForecastDTO);
        if (solarSystemForecastDTO.getId() != null) {
            throw new BadRequestAlertException("A new solarSystemForecast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolarSystemForecastDTO result = solarSystemForecastService.save(solarSystemForecastDTO);
        return ResponseEntity.created(new URI("/api/solar-system-forecasts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solar-system-forecasts : Updates an existing solarSystemForecast.
     *
     * @param solarSystemForecastDTO the solarSystemForecastDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solarSystemForecastDTO,
     * or with status 400 (Bad Request) if the solarSystemForecastDTO is not valid,
     * or with status 500 (Internal Server Error) if the solarSystemForecastDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solar-system-forecasts")
    @Timed
    public ResponseEntity<SolarSystemForecastDTO> updateSolarSystemForecast(@RequestBody SolarSystemForecastDTO solarSystemForecastDTO) throws URISyntaxException {
        log.debug("REST request to update SolarSystemForecast : {}", solarSystemForecastDTO);
        if (solarSystemForecastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolarSystemForecastDTO result = solarSystemForecastService.save(solarSystemForecastDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solarSystemForecastDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solar-system-forecasts : get all the solarSystemForecasts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of solarSystemForecasts in body
     */
    @GetMapping("/solar-system-forecasts")
    @Timed
    public List<SolarSystemForecastDTO> getAllSolarSystemForecasts() {
        log.debug("REST request to get all SolarSystemForecasts");
        return solarSystemForecastService.findAll();
    }

    /**
     * GET  /solar-system-forecasts/:id : get the "id" solarSystemForecast.
     *
     * @param id the id of the solarSystemForecastDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solarSystemForecastDTO, or with status 404 (Not Found)
     */
    @GetMapping("/solar-system-forecasts/{id}")
    @Timed
    public ResponseEntity<SolarSystemForecastDTO> getSolarSystemForecast(@PathVariable Long id) {
        log.debug("REST request to get SolarSystemForecast : {}", id);
        Optional<SolarSystemForecastDTO> solarSystemForecastDTO = solarSystemForecastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solarSystemForecastDTO);
    }

    /**
     * DELETE  /solar-system-forecasts/:id : delete the "id" solarSystemForecast.
     *
     * @param id the id of the solarSystemForecastDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solar-system-forecasts/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolarSystemForecast(@PathVariable Long id) {
        log.debug("REST request to delete SolarSystemForecast : {}", id);
        solarSystemForecastService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
