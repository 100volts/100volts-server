package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.ElectricMeterMonthlyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricMeterMonthlyDataRepository extends JpaRepository<ElectricMeterMonthlyData, Long> {
}
