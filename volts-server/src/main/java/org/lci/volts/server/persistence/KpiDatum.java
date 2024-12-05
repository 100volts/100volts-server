package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "kpi_data")
public class KpiDatum {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kpi_data_id_gen")
    @SequenceGenerator(name = "kpi_data_id_gen", sequenceName = "kpi_data_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value", nullable = false)
    private Float value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kpi", nullable = false)
    private Kpi kpi;

    @Column(name = "ts", nullable = false)
    private OffsetDateTime ts;

}