package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.water.WaterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface WaterDataRepository extends JpaRepository<WaterData, Long> {
    @Query("SELECT u FROM WaterData u WHERE u.water.company.name= ?1 ORDER BY u.value DESC ")
    Optional<List<WaterData>> getAllWaterDataForCompany(String companyName);
}
