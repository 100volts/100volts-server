package org.lci.volts.server.repository.kpi;

import org.lci.volts.server.persistence.kpi.Kpi;
import org.lci.volts.server.persistence.kpi.KpiData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KPIDataRepository extends JpaRepository<KpiData, Long> {
    @Query("select k from KpiData k where k.kpi.company.name= ?1 order by k.ts asc limit 1000")
    Optional<List<KpiData>> getKPIPDataLastMonth(final String companyName);
    @Query("select k from KpiData k where k.kpi.name=?1 and k.kpi.company.name= ?2")
    Optional<List<KpiData>> getKPIPByNameAndCompanyName(final String kpiName,final String companyName);
}
