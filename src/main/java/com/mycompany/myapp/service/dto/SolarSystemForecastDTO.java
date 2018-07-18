package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SolarSystemForecast entity.
 */
public class SolarSystemForecastDTO implements Serializable {

    private Long id;

    private Integer day;

    private String forecast;

    private Long solarSystemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public Long getSolarSystemId() {
        return solarSystemId;
    }

    public void setSolarSystemId(Long solarSystemId) {
        this.solarSystemId = solarSystemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SolarSystemForecastDTO solarSystemForecastDTO = (SolarSystemForecastDTO) o;
        if (solarSystemForecastDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solarSystemForecastDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolarSystemForecastDTO{" +
            "id=" + getId() +
            ", day=" + getDay() +
            ", forecast='" + getForecast() + "'" +
            ", solarSystem=" + getSolarSystemId() +
            "}";
    }
}
