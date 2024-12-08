package org.lci.volts.server.persistence.kpi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kpi_settings")
public class KpiSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kpi_settings_id_gen")
    @SequenceGenerator(name = "kpi_settings_id_gen", sequenceName = "kpi_settings_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "periodic_analysis", nullable = false)
    private org.lci.volts.server.persistence.PeriodicAnalysis periodicAnalysis;

}