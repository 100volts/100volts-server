package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.ElectricMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElMeterRepository extends JpaRepository<ElectricMeter, Long> {

}
