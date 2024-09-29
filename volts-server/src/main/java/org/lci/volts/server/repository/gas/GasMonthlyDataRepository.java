package org.lci.volts.server.repository.gas;

import org.lci.volts.server.persistence.gas.GasMonthlyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GasMonthlyDataRepository extends JpaRepository<GasMonthlyData, Long> {
}
