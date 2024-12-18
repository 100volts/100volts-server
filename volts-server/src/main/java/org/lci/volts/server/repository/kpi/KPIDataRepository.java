package org.lci.volts.server.repository.kpi;

import org.lci.volts.server.persistence.kpi.KpiData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface KPIDataRepository extends JpaRepository<KpiData, Long> {
    @Query("select k from KpiData k where k.kpi.company.name= ?1 AND k.ts BETWEEN ?2 AND ?3 order by k.ts  asc limit 1000")
    Optional<List<KpiData>> getKPIPDataBetweenTs(final String companyName, OffsetDateTime min, OffsetDateTime max);
    @Query("select k from KpiData k where k.kpi.name=?1 and k.kpi.company.name= ?2")
    Optional<List<KpiData>> getKPIPByNameAndCompanyName(final String kpiName,final String companyName);
}
