package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.water.Water;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WaterRepository extends JpaRepository<Water, Long> {
    @Query("SELECT u FROM Water u WHERE u.company.name= ?1 ORDER BY u.ts ASC ")
    Optional<List<Water>> getAllWaterForCompany(final String companyName);
    @Query("SELECT u FROM Water u WHERE u.company.name= ?1 AND u.name=?2 ORDER BY u.ts ASC ")
    Optional<Water> getWaterByNameAndCompanyName(final String companyName, final String name);
}
