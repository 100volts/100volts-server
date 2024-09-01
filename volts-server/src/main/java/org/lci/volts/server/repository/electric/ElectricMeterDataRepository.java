package org.lci.volts.server.repository.electric;

import org.lci.volts.server.persistence.ElectricMeterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ElectricMeterDataRepository  extends JpaRepository<ElectricMeterData, Long> {
    @Query("SELECT u FROM ElectricMeterData u WHERE u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.id desc LIMIT 1")
    Optional<ElectricMeterData> findAllElMetersWitDatalastRead(final int address,final String companyName);
    @Query("SELECT u FROM ElectricMeterData u WHERE u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.date desc LIMIT ?3")
    Optional<List<ElectricMeterData>> findAllElMetersWitDatalastReadLimit(final int address,final String companyName,final int limit);
    @Query("SELECT u FROM ElectricMeterData u WHERE u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.id desc LIMIT 15")
    Optional<Set<ElectricMeterData>> findAvrElMetersData(final int address, final String companyName);
    @Query("SELECT u from ElectricMeterData u where u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.date desc LIMIT 24")
    Optional<List<ElectricMeterData>> findDaielyRead(final int address, final String companyName);
}
