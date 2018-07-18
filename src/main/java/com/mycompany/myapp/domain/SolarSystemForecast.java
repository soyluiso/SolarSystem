package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SolarSystemForecast.
 */
@Entity
@Table(name = "solar_system_forecast")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SolarSystemForecast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "day")
    private Integer day;

    @Column(name = "forecast")
    private String forecast;

    @ManyToOne
    @JsonIgnoreProperties("forecasts")
    private SolarSystem solarSystem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public SolarSystemForecast day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getForecast() {
        return forecast;
    }

    public SolarSystemForecast forecast(String forecast) {
        this.forecast = forecast;
        return this;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public SolarSystemForecast solarSystem(SolarSystem solarSystem) {
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
        SolarSystemForecast solarSystemForecast = (SolarSystemForecast) o;
        if (solarSystemForecast.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solarSystemForecast.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolarSystemForecast{" +
            "id=" + getId() +
            ", day=" + getDay() +
            ", forecast='" + getForecast() + "'" +
            "}";
    }
}
