package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PlanetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Planet and its DTO PlanetDTO.
 */
@Mapper(componentModel = "spring", uses = {SolarSystemMapper.class})
public interface PlanetMapper extends EntityMapper<PlanetDTO, Planet> {

    @Mapping(source = "solarSystem.id", target = "solarSystemId")
    PlanetDTO toDto(Planet planet);

    @Mapping(source = "solarSystemId", target = "solarSystem")
    Planet toEntity(PlanetDTO planetDTO);

    default Planet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Planet planet = new Planet();
        planet.setId(id);
        return planet;
    }
}
