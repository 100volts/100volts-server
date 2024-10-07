package org.lci.volts.server.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Setter
@Getter
@Table(name = "key_performance_indicator_energy")
public class KPIEnergy {
    @Id
    private Long id;
    @Column
    private BigDecimal value;
    @Column
    private BigDecimal index;
}