package com.mycompany.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("complexJobService")
public class ComplexJobService {

    @Autowired
    SolarSystemService solarService;

    public void execute() {

//        utilityService.fetchCountriesDTO();

    }
}
