package org.lci.volts.server.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "electric_meter")
public class ElectricMeter {
    @Id
    public Long id;

    @Column(value = "voltagell1")
    public BigDecimal voltagell1;

    @Column(value = "voltagell2")
    public BigDecimal voltagell2;

    @Column(value = "voltagell3")
    public BigDecimal voltagell3;

    @Column(value = "currentl1")
    public BigDecimal currentl1;

    @Column(value = "currentl2")
    public BigDecimal currentl2;

    @Column(value = "currentl3")
    public BigDecimal currentl3;

    @Column(value = "activepowerl1")
    public BigDecimal activepowerl1;

    @Column(value = "activepowerl2")
    public BigDecimal activepowerl2;

    @Column(value = "activepowerl3")
    public BigDecimal activepowerl3;

    @Column(value = "pfl1")
    public BigDecimal pfl1;

    @Column(value = "pfl2")
    public BigDecimal pfl2;

    @Column(value = "pfl3")
    public BigDecimal pfl3;

    @Column(value = "pf")
    public BigDecimal pf;

    @Column(value = "totalactiveenergyimporttariff1")
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