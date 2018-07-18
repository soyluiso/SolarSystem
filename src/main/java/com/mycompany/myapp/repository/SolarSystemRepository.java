package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SolarSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SolarSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolarSystemRepository extends JpaRepository<SolarSystem, Long> {

}
