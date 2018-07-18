package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SolarSystem.
 */
@Entity
@Table(name = "solar_system")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SolarSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "system_name")
    private String systemName;

    @OneToMany(mappedBy = "solarSystem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Planet> planets = new HashSet<>();

    @OneToMany(mappedBy = "solarSystem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SolarSystemForecast> forecasts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public SolarSystem systemName(String systemName) {
        this.systemName = systemName;
        return this;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Set<Planet> getPlanets() {
        return planets;
    }

    public SolarSystem planets(Set<Planet> planets) {
        this.planets = planets;
        return this;
    }

    public SolarSystem addPlanet(Planet planet) {
        this.planets.add(planet);
        planet.setSolarSystem(this);
        return this;
    }

    public SolarSystem removePlanet(Planet planet) {
        this.planets.remove(planet);
        planet.setSolarSystem(null);
        return this;
    }

    public void setPlanets(Set<Planet> planets) {
        this.planets = planets;
    }

    public Set<SolarSystemForecast> getForecasts() {
        return forecasts;
    }

    public SolarSystem forecasts(Set<SolarSystemForecast> solarSystemForecasts) {
        this.forecasts = solarSystemForecasts;
        return this;
    }

    public SolarSystem addForecast(SolarSystemForecast solarSystemForecast) {
        this.forecasts.add(solarSystemForecast);
        solarSystemForecast.setSolarSystem(this);
        return this;
    }

    public SolarSystem removeForecast(SolarSystemForecast solarSystemForecast) {
        this.forecasts.remove(solarSystemForecast);
        solarSystemForecast.setSolarSystem(null);
        return this;
    }

    public void setForecasts(Set<SolarSystemForecast> solarSystemForecasts) {
        this.forecasts = solarSystemForecasts;
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
        SolarSystem solarSystem = (SolarSystem) o;
        if (solarSystem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solarSystem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolarSystem{" +
            "id=" + getId() +
            ", systemName='" + getSystemName() + "'" +
            "}";
    }
}
