package org.lci.volts.server.repository.gas;

import org.lci.volts.server.persistence.gas.GasMonthlyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GasMonthlyDataRepository extends JpaRepository<GasMonthlyData, Long> {
    @Query("SELECT u FROM GasMonthlyData u WHERE u.gas.name=?1 AND u.gas.company.name= ?2 ORDER BY u.id LIMIT 6")
    Optional<List<GasMonthlyData>> findAll6Back(String gasMeterName, String companyName);
}
