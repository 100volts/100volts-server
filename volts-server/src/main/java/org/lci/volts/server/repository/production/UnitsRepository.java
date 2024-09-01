package org.lci.volts.server.repository.production;

import org.lci.volts.server.persistence.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UnitsRepository extends JpaRepository<Units, Long> {
    @Query("SELECT u FROM Units u WHERE u.name= ?1 ORDER BY u.id ASC Limit 1")
    Optional<Units> findByName(String name);
}
