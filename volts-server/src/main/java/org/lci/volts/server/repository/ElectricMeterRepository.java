package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.CompanyUser;
import org.lci.volts.server.persistence.ElectricMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ElectricMeterRepository extends JpaRepository<ElectricMeter, Long> {
    @Query("SELECT u FROM ElectricMeter u WHERE u.address= ?1")
    Optional<ElectricMeter> findByAddress(int address);
}
