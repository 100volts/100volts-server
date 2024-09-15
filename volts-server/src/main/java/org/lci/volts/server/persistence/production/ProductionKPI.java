package org.lci.volts.server.persistence.production;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.KPIEnergy;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "key_performance_indicator_production")
public class ProductionKPI {
    @Id
    private Long id;
    @Column
    private BigDecimal value;
    @Column
    private BigDecimal target;
    @Column(name = "ts")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "production")
    private Production production;
    @ManyToOne
    @JoinColumn(name = "energy")
    private KPIEnergy energy;
}
