package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.Energy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KPIEnergyRepository extends JpaRepository<Energy, Long>{
}
