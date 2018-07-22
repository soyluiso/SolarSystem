package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SolarSystemForecast;
import com.mycompany.myapp.service.dto.SolarSystemForecastDTO;
import com.mycompany.myapp.service.dto.SolarSystemStateDTO;
import com.mycompany.myapp.service.mapper.SolarSystemForecastMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mycompany.myapp.service.mapper.SolarSystemForecastMapper.*;

@Component("complexJobService")
public class ComplexJobService {

    @Autowired
    SolarSystemService solarService;
    @Autowired
    SolarSystemForecastService solarSystemForecastService;
    @Autowired
    SolarSystemForecastMapper solarSystemForecastMapper;

    public void execute() {
        for (int i = 0 ; i < 361 ; i++) {
            SolarSystemStateDTO dto = solarService.calculateWeatherState(Long.valueOf(1001), i);

            SolarSystemForecast solarSystemForecast = solarSystemForecastService.findBySolarSystemAndDay(Long.valueOf(1001), i);

            if (solarSystemForecast == null) {
                SolarSystemForecastDTO newDto = new SolarSystemForecastDTO();

                newDto.setDay(i);
                newDto.setForecast(dto.getWeather().toString());
                newDto.setSolarSystemId(Long.valueOf(1001));

                solarSystemForecastService.save(newDto);
            } else if (solarSystemForecast != null && !solarSystemForecast.getForecast().equals(dto.getWeather().toString())) {

                SolarSystemForecastDTO newDto = solarSystemForecastMapper.toDto(solarSystemForecast);
                newDto.setForecast(dto.getWeather().toString());

                solarSystemForecastService.save(newDto);
            }
        }

    }
}
