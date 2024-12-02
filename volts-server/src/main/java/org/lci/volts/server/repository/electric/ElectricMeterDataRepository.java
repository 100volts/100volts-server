package org.lci.volts.server.repository.electric;

import org.lci.volts.server.persistence.electric.ElectricMeterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ElectricMeterDataRepository  extends JpaRepository<ElectricMeterData, Long> {
    @Query("SELECT u FROM ElectricMeterData u WHERE u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.id desc LIMIT 1")
    Optional<ElectricMeterData> findAllElMetersWitDatalastRead(final int address,final String companyName);
    @Query("SELECT u FROM ElectricMeterData u WHERE u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.date desc LIMIT ?3")
    Optional<List<ElectricMeterData>> findAllElMetersWitDatalastReadLimit(final int address,final String companyName,final int limit);
    @Query("SELECT u FROM ElectricMeterData u WHERE u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.id desc LIMIT 15")
    Optional<List<ElectricMeterData>> findAvrElMetersData(final int address, final String companyName);
    @Query("SELECT u from ElectricMeterData u where u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.date desc LIMIT 24")
    Optional<List<ElectricMeterData>> findDaielyRead(final int address, final String companyName);
    @Query("SELECT u from ElectricMeterData u where u.meter.address= ?1 AND u.meter.company.name= ?2 AND u.date> ?3 ORDER BY u.date desc")
    Optional<List<ElectricMeterData>> findDataunderOneMonth(final int address, final String companyName, LocalDateTime limit);
    @Query(value = "SELECT e FROM ElectricMeterData e WHERE e.meter.address= ?1 AND e.meter.company.name= ?2 AND e.date BETWEEN ?3 AND ?4 ORDER BY e.date DESC LIMIT 1")
    Optional<ElectricMeterData> getYesterdays(int address, String companyName, LocalDateTime startOfYesterday, LocalDateTime endOfYesterday);
    @Query("SELECT u from ElectricMeterData u where u.meter.company.name= ?1 ORDER BY u.date desc LIMIT ?2")
    Optional<List<ElectricMeterData>> fiendAllElDataForACompany( final String companyName, final int limit);
}
