package org.lci.volts.server.repository.electric;

import org.lci.volts.server.persistence.ElectricMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ElectricMeterRepository extends JpaRepository<ElectricMeter, Long> {
    @Query("SELECT u FROM ElectricMeter u WHERE u.address= ?1")
    Optional<ElectricMeter> findByAddress(int address);

    @Query("SELECT u FROM ElectricMeter u WHERE u.company.name= ?1 ORDER BY u.address ASC ")
    Optional<Set<ElectricMeter>> findAllElMetersByCompanyName(String name);
}
