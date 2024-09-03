package org.lci.volts.server.repository.electric;

import org.lci.volts.server.persistence.electric.ElectricMeterMonthlyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricMeterMonthlyDataRepository extends JpaRepository<ElectricMeterMonthlyData, Long> {
}
