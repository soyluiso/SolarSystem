package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Planet.
 */
@Entity
@Table(name = "planet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Planet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "planet_name")
    private String planetName;

    @Column(name = "planet_velocity")
    private Long planetVelocity;

    @Column(name = "planet_radius")
    private Long planetRadius;

    @ManyToOne
    @JsonIgnoreProperties("planets")
    private SolarSystem solarSystem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanetName() {
        return planetName;
    }

    public Planet planetName(String planetName) {
        this.planetName = planetName;
        return this;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public Long getPlanetVelocity() {
        return planetVelocity;
    }

    public Planet planetVelocity(Long planetVelocity) {
        this.planetVelocity = planetVelocity;
        return this;
    }

    public void setPlanetVelocity(Long planetVelocity) {
        this.planetVelocity = planetVelocity;
    }

    public Long getPlanetRadius() {
        return planetRadius;
    }

    public Planet planetRadius(Long planetRadius) {
        this.planetRadius = planetRadius;
        return this;
    }

    public void setPlanetRadius(Long planetRadius) {
        this.planetRadius = planetRadius;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public Planet solarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
        return this;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Planet planet = (Planet) o;
        if (planet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Planet{" +
            "id=" + getId() +
            ", planetName='" + getPlanetName() + "'" +
            ", planetVelocity=" + getPlanetVelocity() +
            ", planetRadius=" + getPlanetRadius() +
            "}";
    }

    /**
     * Get the point of a Planet on a given day on R2
     *
     * @param day the day
     * @return a pair of values on R2
     */
    public BigDecimal[] getPointInDay(int day) {

        BigDecimal degrees = BigDecimal.valueOf(this.getPlanetVelocity()).multiply(BigDecimal.valueOf(day));

        BigDecimal x = BigDecimal.valueOf(this.getPlanetRadius()).multiply(BigDecimal.valueOf(Math.sin(Math.toRadians(degrees.doubleValue()))));

        BigDecimal y = BigDecimal.valueOf(this.getPlanetRadius()).multiply(BigDecimal.valueOf(Math.cos(Math.toRadians(degrees.doubleValue()))));

        return (new BigDecimal[]{x.setScale(5, BigDecimal.ROUND_HALF_UP), y.setScale(5, BigDecimal.ROUND_HALF_UP)});
    }
}
