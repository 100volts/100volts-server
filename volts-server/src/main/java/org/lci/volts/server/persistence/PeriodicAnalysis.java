package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "periodic_analysis")
public class PeriodicAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "periodic_analysis_id_gen")
    @SequenceGenerator(name = "periodic_analysis_id_gen", sequenceName = "periodic_analysis_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

}