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
    private Long id;
    @Column
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "production")
    private Production production;
}
