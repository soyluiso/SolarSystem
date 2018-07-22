package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SolarSystemForecast;
import com.mycompany.myapp.service.dto.SolarSystemForecastDTO;
import com.mycompany.myapp.service.dto.SolarSystemStateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("complexJobService")
public class ComplexJobService {

    @Autowired
    SolarSystemService solarService;
    @Autowired
    SolarSystemForecastService solarSystemForecastService;

    public void execute() {
        for (int i = 0 ; i < 361 ; i++) {
            SolarSystemStateDTO dto = solarService.calculateWeatherState(Long.valueOf(1001), i);

            SolarSystemForecast solarSystemForecast = solarSystemForecastService.findBySolarSystemAndDay(Long.valueOf(1001), i);

            if (solarSystemForecast == null || !solarSystemForecast.getForecast().equals(dto.getWeather().toString())) {
                SolarSystemForecastDTO newDto = new SolarSystemForecastDTO();

                newDto.setDay(i);
                newDto.setForecast(dto.getWeather().toString());
                newDto.setSolarSystemId(Long.valueOf(1001));

                if (solarSystemForecast!= null){
                    newDto.setId(solarSystemForecast.getId());
                }

                solarSystemForecastService.save(newDto);
            }
        }

    }
}
