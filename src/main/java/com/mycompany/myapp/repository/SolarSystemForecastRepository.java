package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SolarSystemForecast;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SolarSystemForecast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolarSystemForecastRepository extends JpaRepository<SolarSystemForecast, Long> {

}
