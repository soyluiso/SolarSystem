package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.List;

public class SolarSystemFullForecastDTO implements Serializable {

    private Long solarSystemId;
    private List<String> rain;
    private List<String> maxRain;
    private List<String> drought;
    private List<String> optimal;

    public Long getSolarSystemId() {
        return solarSystemId;
    }

    public void setSolarSystemId(Long solarSystemId) {
        this.solarSystemId = solarSystemId;
    }

    public List<String> getRain() {
        return rain;
    }

    public void setRain(List<String> rain) {
        this.rain = rain;
    }

    public List<String> getMaxRain() {
        return maxRain;
    }

    public void setMaxRain(List<String> maxRain) {
        this.maxRain = maxRain;
    }

    public List<String> getDrought() {
        return drought;
    }

    public void setDrought(List<String> drought) {
        this.drought = drought;
    }

    public List<String> getOptimal() {
        return optimal;
    }

    public void setOptimal(List<String> optimal) {
        this.optimal = optimal;
    }


    public SolarSystemFullForecastDTO(Long solarSystemId, List<String> rain, List<String> maxRain, List<String> drought, List<String> optimal){
        this.solarSystemId = solarSystemId;
        this.rain = rain;
        this.maxRain = maxRain;
        this.drought = drought;
        this.optimal = optimal;
    }

    @Override
    public String toString() {
        return "SolarSystemFullForecastDTO{" +
            "rain=" + getRain() +
            ", maxRain=" + getMaxRain() +
            ", drought='" + getDrought() + "'" +
            ", optimal=" + getOptimal() +
            "}";
    }
}
