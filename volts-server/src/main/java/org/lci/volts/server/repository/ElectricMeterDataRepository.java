package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.ElectricMeterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ElectricMeterDataRepository  extends JpaRepository<ElectricMeterData, Long> {

    @Query("SELECT u FROM ElectricMeterData u WHERE u.meter.address= ?1 AND u.meter.company.name= ?2 ORDER BY u.id desc LIMIT 1")
    Optional<ElectricMeterData> findAllElMetersWitDatalastRead(final int address,final String companyName);
}
