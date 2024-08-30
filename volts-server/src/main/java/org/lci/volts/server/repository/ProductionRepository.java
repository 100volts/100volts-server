package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.ElectricMeter;
import org.lci.volts.server.persistence.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    @Query("SELECT u FROM Production u WHERE u.name= ?1 AND u.company.name= ?2 ORDER BY u.id ASC Limit 1")
    Optional<Production> findAllElMetersByCompanyName(final String name,final String companyName);
}
