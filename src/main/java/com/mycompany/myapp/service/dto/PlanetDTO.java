package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Planet;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Planet entity.
 */
public class PlanetDTO implements Serializable {

    private Long id;

    private String planetName;

    private Long planetVelocity;

    private Long planetRadius;

    private Long solarSystemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public Long getPlanetVelocity() {
        return planetVelocity;
    }

    public void setPlanetVelocity(Long planetVelocity) {
        this.planetVelocity = planetVelocity;
    }

    public Long getPlanetRadius() {
        return planetRadius;
    }

    public void setPlanetRadius(Long planetRadius) {
        this.planetRadius = planetRadius;
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

        PlanetDTO planetDTO = (PlanetDTO) o;
        if (planetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanetDTO{" +
            "id=" + getId() +
            ", planetName='" + getPlanetName() + "'" +
            ", planetVelocity=" + getPlanetVelocity() +
            ", planetRadius=" + getPlanetRadius() +
            ", solarSystem=" + getSolarSystemId() +
            "}";
    }
}
