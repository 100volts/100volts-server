package org.lci.volts.server.repository.electric;

import org.lci.volts.server.persistence.electric.ElectricMeterEnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ElectricMeterEnergyDataRepository extends JpaRepository<ElectricMeterEnergyData, Long> {
    @Query("SELECT u from ElectricMeterEnergyData u where u.meter.company.name= ?1 ORDER BY u.recordedAt desc")
    Optional<List<ElectricMeterEnergyData>> findEnergyDataForMeter(String s);
    @Query("SELECT u from ElectricMeterEnergyData u where u.meter.company.name= ?1 AND u.meter.name = ?2" +
            " ORDER BY u.recordedAt desc")
    Optional<List<ElectricMeterEnergyData>> findEnergyDataForMeterFilterByMeterName(String companyName, String meterName);
}
