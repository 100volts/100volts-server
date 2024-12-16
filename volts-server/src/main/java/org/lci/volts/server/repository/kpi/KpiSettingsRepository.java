package org.lci.volts.server.repository.kpi;

import org.lci.volts.server.persistence.kpi.KpiSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KpiSettingsRepository extends JpaRepository<KpiSetting, Long> {
    @Query("SELECT u FROM KpiSetting u WHERE u.periodicAnalysis.name= ?1")
    Optional<KpiSetting> findByName(final String name);
}
