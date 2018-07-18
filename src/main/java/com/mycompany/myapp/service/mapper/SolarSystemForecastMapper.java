package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SolarSystemForecastDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SolarSystemForecast and its DTO SolarSystemForecastDTO.
 */
@Mapper(componentModel = "spring", uses = {SolarSystemMapper.class})
public interface SolarSystemForecastMapper extends EntityMapper<SolarSystemForecastDTO, SolarSystemForecast> {

    @Mapping(source = "solarSystem.id", target = "solarSystemId")
    SolarSystemForecastDTO toDto(SolarSystemForecast solarSystemForecast);

    @Mapping(source = "solarSystemId", target = "solarSystem")
    SolarSystemForecast toEntity(SolarSystemForecastDTO solarSystemForecastDTO);

    default SolarSystemForecast fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolarSystemForecast solarSystemForecast = new SolarSystemForecast();
        solarSystemForecast.setId(id);
        return solarSystemForecast;
    }
}
