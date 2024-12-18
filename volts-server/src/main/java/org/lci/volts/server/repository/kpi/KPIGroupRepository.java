package org.lci.volts.server.repository.kpi;

import org.lci.volts.server.persistence.kpi.KpiGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KPIGroupRepository extends JpaRepository<KpiGroup, Long> {
    @Query("SELECT u FROM KpiGroup u WHERE u.name= ?1")
    Optional<KpiGroup> findByName(final String name);
    @Query("SELECT u FROM KpiGroup u WHERE u.company.name= ?1")
    Optional<List<KpiGroup>> findByCompanyName(String company);
}
