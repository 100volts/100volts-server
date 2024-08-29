package org.lci.volts.server.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Setter
@Getter
@Table(name = "electric_meter_monthly_data")
public class ElectricMeterMonthlyData {
    @Id
    private Long id;
    @Column(name="energy_import_tariff_1")
    private BigDecimal tariff1;
    @Column(name="energy_import_tariff_2")
    private BigDecimal tarif2;
    @Column
    private Date tz;
}
