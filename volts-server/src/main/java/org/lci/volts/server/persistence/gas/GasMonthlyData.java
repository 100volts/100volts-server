package org.lci.volts.server.persistence.gas;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Setter
@Getter
@Table(name = "gas_monthly_data")
public class GasMonthlyData {
    @Id
    private Long id;
    @Column(name = "value_meter")
    private BigDecimal valueMeter;
    @Column(name = "value_tariff")
    private BigDecimal valueTariff;
    @Column(name = "ts")
    private Date ts;
}
