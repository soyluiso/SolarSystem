package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SolarSystem entity.
 */
public class SolarSystemDTO implements Serializable {

    private Long id;

    private String systemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SolarSystemDTO solarSystemDTO = (SolarSystemDTO) o;
        if (solarSystemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solarSystemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolarSystemDTO{" +
            "id=" + getId() +
            ", systemName='" + getSystemName() + "'" +
            "}";
    }
}
