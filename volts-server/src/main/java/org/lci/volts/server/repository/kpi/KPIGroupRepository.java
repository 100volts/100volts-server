package org.lci.volts.server.repository.kpi;

import org.lci.volts.server.persistence.kpi.KpiGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KPIGroupRepository extends JpaRepository<KpiGroup, Long> {
    @Query("SELECT u FROM KpiGroup u WHERE u.name= ?1")
    Optional<KpiGroup> findByName(final String name);
}
