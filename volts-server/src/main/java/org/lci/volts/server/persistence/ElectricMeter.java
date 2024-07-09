package org.lci.volts.server.persistence;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Table(name = "electric_meter")
@AllArgsConstructor
@NoArgsConstructor
public class ElectricMeter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public BigDecimal voltagell1;

    @Column
    public BigDecimal voltagell2;

    @Column
    public BigDecimal voltagell3;

    @Column
    public BigDecimal currentl1;

    @Column
    public BigDecimal currentl2;

    @Column
    public BigDecimal currentl3;

    @Column
    public BigDecimal activepowerl1;

    @Column
    public BigDecimal activepowerl2;

    @Column
    public BigDecimal activepowerl3;

    @Column
    public BigDecimal pfl1;

    @Column
    public BigDecimal pfl2;

    @Column
    public BigDecimal pfl3;

    @Column
    public BigDecimal pf;

    @Column
    public BigDecimal totalactiveenergyimporttariff1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getVoltagell1() {
        return voltagell1;
    }

    public void setVoltagell1(BigDecimal voltagell1) {
        this.voltagell1 = voltagell1;
    }

    public BigDecimal getVoltagell2() {
        return voltagell2;
    }

    public void setVoltagell2(BigDecimal voltagell2) {
        this.voltagell2 = voltagell2;
    }

    public BigDecimal getVoltagell3() {
        return voltagell3;
    }

    public void setVoltagell3(BigDecimal voltagell3) {
        this.voltagell3 = voltagell3;
    }

    public BigDecimal getCurrentl1() {
        return currentl1;
    }

    public void setCurrentl1(BigDecimal currentl1) {
        this.currentl1 = currentl1;
    }

    public BigDecimal getCurrentl2() {
        return currentl2;
    }

    public void setCurrentl2(BigDecimal currentl2) {
        this.currentl2 = currentl2;
    }

    public BigDecimal getCurrentl3() {
        return currentl3;
    }

    public void setCurrentl3(BigDecimal currentl3) {
        this.currentl3 = currentl3;
    }

    public BigDecimal getActivepowerl1() {
        return activepowerl1;
    }

    public void setActivepowerl1(BigDecimal activepowerl1) {
        this.activepowerl1 = activepowerl1;
    }

    public BigDecimal getActivepowerl2() {
        return activepowerl2;
    }

    public void setActivepowerl2(BigDecimal activepowerl2) {
        this.activepowerl2 = activepowerl2;
    }

    public BigDecimal getActivepowerl3() {
        return activepowerl3;
    }

    public void setActivepowerl3(BigDecimal activepowerl3) {
        this.activepowerl3 = activepowerl3;
    }

    public BigDecimal getPfl1() {
        return pfl1;
    }

    public void setPfl1(BigDecimal pfl1) {
        this.pfl1 = pfl1;
    }

    public BigDecimal getPfl2() {
        return pfl2;
    }

    public void setPfl2(BigDecimal pfl2) {
        this.pfl2 = pfl2;
    }

    public BigDecimal getPfl3() {
        return pfl3;
    }

    public void setPfl3(BigDecimal pfl3) {
        this.pfl3 = pfl3;
    }

    public BigDecimal getPf() {
        return pf;
    }

    public void setPf(BigDecimal pf) {
        this.pf = pf;
    }

    public BigDecimal getTotalactiveenergyimporttariff1() {
        return totalactiveenergyimporttariff1;
    }

    public void setTotalactiveenergyimporttariff1(BigDecimal totalactiveenergyimporttariff1) {
        this.totalactiveenergyimporttariff1 = totalactiveenergyimporttariff1;
    }
}