package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SolarSystemForecast;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SolarSystemForecast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolarSystemForecastRepository extends JpaRepository<SolarSystemForecast, Long> {

    @Query("select solarSystemForecast from SolarSystemForecast solarSystemForecast where solarSystemForecast.solarSystem.id = :id and solarSystemForecast.day = :day ")
    SolarSystemForecast findBySolarSystemAndDay(@Param("id") Long id, @Param("day") int day);

}
