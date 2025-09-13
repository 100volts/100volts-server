package org.lci.volts.server.repository.electric;

import org.lci.volts.server.persistence.electric.ElectricMeterEnergyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricMeterEnergyDataRepository extends JpaRepository<ElectricMeterEnergyData, Long> {
}
