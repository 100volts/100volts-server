package org.lci.volts.server.repository.gas;

import org.lci.volts.server.persistence.gas.GasData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GasDataRepository extends JpaRepository<GasData, Long> {
    @Query("SELECT u FROM GasData u WHERE u.gas.company.name= ?1 ORDER BY u.value DESC ")
    Optional<List<GasData>> getAllGasDataForCompany(String companyName);
    @Query("SELECT u FROM GasData u WHERE u.gas.company.name= ?1 AND u.ts> ?2 AND u.ts< ?3 ORDER BY u.ts ASC ")
    Optional<List<GasData>> getAllGasForCompanyForData(final String companyName, final Date start, final Date end);
    @Query("SELECT u FROM GasData u WHERE u.gas.company.name= ?1 AND u.gas.name= ?2 ORDER BY u.ts DESC ")
    Optional<List<GasData>> getGasReport(String companyName, String gasMeterName);
    @Query("SELECT u FROM GasData u WHERE u.gas.company.name= ?1 AND u.gas.name= ?2 AND u.value=?3 ORDER BY u.ts ASC ")
    Optional<GasData> getData(String companyName, String meterName, BigDecimal gasDataValue);
    @Query(value = "SELECT e FROM GasData e WHERE e.gas.name= ?1 AND e.gas.company.name= ?2 AND e.ts BETWEEN ?3 AND ?4 ORDER BY e.ts DESC LIMIT 1")
    Optional<GasData> getYesterdays(String gasMeterName, String companyName, LocalDateTime startOfYesterday, LocalDateTime endOfYesterday);
    @Query("SELECT u FROM GasData u WHERE u.gas.name= ?1 AND u.gas.company.name= ?2 ORDER BY u.id desc LIMIT 1")
    Optional<GasData> findAllGasMetersWitDataLastRead(String gasMeterName, String companyName);
}