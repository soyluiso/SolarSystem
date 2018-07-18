package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.WeatherState;

import java.io.Serializable;

public class SolarSystemStateDTO implements Serializable {

    private int day;
    private WeatherState weather;


    public SolarSystemStateDTO(int day, WeatherState weatherState){
        this.day = day;
        this.weather = weatherState;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public WeatherState getWeather() {
        return weather;
    }

    public void setWeather(WeatherState weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "SolarSystemStateDTO{" +
            "day=" + getDay() +
            ", weather='" + getWeather() +
            "}";
    }


}
