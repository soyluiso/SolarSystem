package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SolarSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SolarSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolarSystemRepository extends JpaRepository<SolarSystem, Long> {

    @Query("select solarSystem from SolarSystem solarSystem where solarSystem.id = :id ")
    SolarSystem find(@Param("id") Long id);

}
