package org.lci.volts.server.persistence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "electric_meter_data")
public class ElectricMeterData {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "el_meter")
    private ElectricMeter meter;
    @Column(name = "voltage_l1_n")
    private double voltageL1;
    @Column(name = "voltage_l2_n")
    private double voltageL2;
    @Column(name = "voltage_l3_n")
    private double voltageL3;
    @Column(name = "current_l1")
    private double currentL1;
    @Column(name = "current_l2")
    private double currentL2;
    @Column(name = "current_l3")
    private double currentL3;
    @Column(name = "active_power_l1")
    private double activePowerL1;
    @Column(name = "active_power_l2")
    private double activePowerL2;
    @Column(name = "active_power_l3")
    private double activePowerL3;
    @Column(name = "power_factor_l1")
    private double powerFactorL1;
    @Column(name = "power_factor_l2")
    private double powerFactorL2;
    @Column(name = "power_factor_l3")
    private double powerFactorL3;
    @Column(name = "total_active_power")
    private double totalActivePower;
    @Column(name = "total_active_energy_import_tariff_1")
    private double totalActiveEnergyImportTariff1;
    @Column(name = "total_active_energy_import_tariff_2")
    private double totalActiveEnergyImportTariff2;
}
