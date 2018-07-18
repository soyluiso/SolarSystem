package com.mycompany.myapp.service.dto;

public class SolarSystemStateDTO {

    enum WeatherState{
        RAIN, DROUGHT, OPTIMUM, NORMAL
    }

    private int days;
    private WeatherState weatherState;

    public SolarSystemStateDTO(int days, WeatherState weatherState){
        this.days = days;
        this.weatherState = weatherState;
    }
}
