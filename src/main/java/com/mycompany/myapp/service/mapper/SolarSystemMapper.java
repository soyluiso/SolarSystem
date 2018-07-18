package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SolarSystemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SolarSystem and its DTO SolarSystemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SolarSystemMapper extends EntityMapper<SolarSystemDTO, SolarSystem> {


    @Mapping(target = "planets", ignore = true)
    @Mapping(target = "forecasts", ignore = true)
    SolarSystem toEntity(SolarSystemDTO solarSystemDTO);

    default SolarSystem fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolarSystem solarSystem = new SolarSystem();
        solarSystem.setId(id);
        return solarSystem;
    }
}
