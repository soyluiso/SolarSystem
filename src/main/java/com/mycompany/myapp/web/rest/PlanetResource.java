package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.PlanetService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.PlanetDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Planet.
 */
@RestController
@RequestMapping("/api")
public class PlanetResource {

    private final Logger log = LoggerFactory.getLogger(PlanetResource.class);

    private static final String ENTITY_NAME = "planet";

    private final PlanetService planetService;

    public PlanetResource(PlanetService planetService) {
        this.planetService = planetService;
    }

    /**
     * POST  /planets : Create a new planet.
     *
     * @param planetDTO the planetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planetDTO, or with status 400 (Bad Request) if the planet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planets")
    @Timed
    public ResponseEntity<PlanetDTO> createPlanet(@RequestBody PlanetDTO planetDTO) throws URISyntaxException {
        log.debug("REST request to save Planet : {}", planetDTO);
        if (planetDTO.getId() != null) {
            throw new BadRequestAlertException("A new planet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanetDTO result = planetService.save(planetDTO);
        return ResponseEntity.created(new URI("/api/planets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planets : Updates an existing planet.
     *
     * @param planetDTO the planetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planetDTO,
     * or with status 400 (Bad Request) if the planetDTO is not valid,
     * or with status 500 (Internal Server Error) if the planetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planets")
    @Timed
    public ResponseEntity<PlanetDTO> updatePlanet(@RequestBody PlanetDTO planetDTO) throws URISyntaxException {
        log.debug("REST request to update Planet : {}", planetDTO);
        if (planetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanetDTO result = planetService.save(planetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planets : get all the planets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of planets in body
     */
    @GetMapping("/planets")
    @Timed
    public ResponseEntity<List<PlanetDTO>> getAllPlanets(Pageable pageable) {
        log.debug("REST request to get a page of Planets");
        Page<PlanetDTO> page = planetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/planets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /planets/:id : get the "id" planet.
     *
     * @param id the id of the planetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/planets/{id}")
    @Timed
    public ResponseEntity<PlanetDTO> getPlanet(@PathVariable Long id) {
        log.debug("REST request to get Planet : {}", id);
        Optional<PlanetDTO> planetDTO = planetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planetDTO);
    }

    /**
     * GET  /planets/:id : get the "id" planet position.
     *
     * @param id the id of the planetDTO to retrieve
     * @param day the day to check position
     * @return the double array with the position
     */
    @GetMapping("/planets/{id}/position")
    @Timed
    public BigDecimal[] getPlanetOnADay(@PathVariable Long id, int day) {
        log.debug("REST request to get Planet position: {}", id);
        //Optional<PlanetDTO> planetDTO = planetService.findOne(id);

        return planetService.getPointInDay(id, day);
    }

    /**
     * DELETE  /planets/:id : delete the "id" planet.
     *
     * @param id the id of the planetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planets/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanet(@PathVariable Long id) {
        log.debug("REST request to delete Planet : {}", id);
        planetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
