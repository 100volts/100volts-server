package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "energy")
public class Energy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "energy_id_gen")
    @SequenceGenerator(name = "energy_id_gen", sequenceName = "energy_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "energy_index", nullable = false)
    private Double energyIndex;

    @Column(name = "ts", nullable = false)
    private OffsetDateTime ts;

}