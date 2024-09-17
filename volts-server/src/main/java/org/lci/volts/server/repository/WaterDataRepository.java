package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.water.WaterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WaterDataRepository extends JpaRepository<WaterData, Long> {
    @Query("SELECT u FROM WaterData u WHERE u.water.company.name= ?1 ORDER BY u.value DESC ")
    Optional<List<WaterData>> getAllWaterDataForCompany(String companyName);
    @Query("SELECT u FROM WaterData u WHERE u.water.company.name= ?1 AND u.water.name= ?2 ORDER BY u.value DESC LIMIT 100")
    Optional<List<WaterData>> getAllWaterDataReport(String companyName, String meterName);
    @Query("SELECT u FROM WaterData u WHERE u.water.company.name= ?1 AND u.water.name= ?2 AND u.value= ?3  ORDER BY u.value LIMIT 1")
    Optional<WaterData> getAllWaterDataForDelete(String companyName, String meterName, BigDecimal value);
}
