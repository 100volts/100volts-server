package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.ElMeterDataDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
    private BigDecimal voltageL1;
    @Column(name = "voltage_l2_n")
    private BigDecimal voltageL2;
    @Column(name = "voltage_l3_n")
    private BigDecimal voltageL3;
    @Column(name = "current_l1")
    private BigDecimal currentL1;
    @Column(name = "current_l2")
    private BigDecimal currentL2;
    @Column(name = "current_l3")
    private BigDecimal currentL3;
    @Column(name = "active_power_l1")
    private BigDecimal activePowerL1;
    @Column(name = "active_power_l2")
    private BigDecimal activePowerL2;
    @Column(name = "active_power_l3")
    private BigDecimal activePowerL3;
    @Column(name = "power_factor_l1")
    private BigDecimal powerFactorL1;
    @Column(name = "power_factor_l2")
    private BigDecimal powerFactorL2;
    @Column(name = "power_factor_l3")
    private BigDecimal powerFactorL3;
    @Column(name = "total_active_power")
    private BigDecimal totalActivePower;
    @Column(name = "total_active_energy_import_tariff_1")
    private BigDecimal totalActiveEnergyImportTariff1;
    @Column(name = "total_active_energy_import_tariff_2")
    private BigDecimal totalActiveEnergyImportTariff2;
    @Column(name= "time_stamp")
    private LocalDateTime date;

    public ElMeterDataDTO toDTO() {
        return new ElMeterDataDTO(
                BigDecimal.valueOf(this.getMeter().getId()),
                this.getVoltageL1(), this.getVoltageL2(),this.getVoltageL3(),
                this.getCurrentL1(), this.getCurrentL2(),this.getCurrentL3(),
                this.getActivePowerL1(), this.getActivePowerL2(),this.getActivePowerL3(),
                this.getPowerFactorL1(), this.getPowerFactorL2(), this.getPowerFactorL3(),
                this.getTotalActivePower(),
                this.getTotalActiveEnergyImportTariff1(),
                this.getTotalActiveEnergyImportTariff2());
    }
}
