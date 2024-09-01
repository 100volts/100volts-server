package org.lci.volts.server.persistence.production;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "production_data")
public class ProductionData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_data_seq_gen")
    @SequenceGenerator(name = "production_data_seq_gen", sequenceName = "production_data_seq", allocationSize = 1)
    private Long id;
    @Column
    private BigDecimal value;
    @Column
    private long production;
}
