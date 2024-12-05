package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.persistence.production.Production;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "kpi")
public class Kpi {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kpi_id_gen")
    @SequenceGenerator(name = "kpi_id_gen", sequenceName = "kpi_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "descriptor", nullable = false, length = 200)
    private String descriptor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "energy", nullable = false)
    private Energy energy;

    @Column(name = "ts", nullable = false)
    private OffsetDateTime ts;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "production", nullable = false)
    private Production production;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settings", nullable = false)
    private org.lci.volts.server.persistence.KpiSetting settings;

    @Column(name = "target", nullable = false)
    private Double target;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_kpi", nullable = false)
    private org.lci.volts.server.persistence.KpiGroup groupKpi;

}