package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kpi_group")
public class KpiGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kpi_group_id_gen")
    @SequenceGenerator(name = "kpi_group_id_gen", sequenceName = "kpi_group_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "descriptor", nullable = false, length = 200)
    private String descriptor;

}