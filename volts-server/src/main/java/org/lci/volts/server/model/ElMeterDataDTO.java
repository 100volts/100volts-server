package org.lci.volts.server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Data
public class ElMeterDataDTO {
    public BigDecimal voltagell1;
    public BigDecimal voltagell2;
    public BigDecimal voltagell3;
    public BigDecimal currentl1;
    public BigDecimal currentl2;
    public BigDecimal currentl3;
    public BigDecimal activepowerl1;
    public BigDecimal activepowerl2;
    public BigDecimal activepowerl3;
    public BigDecimal pfl1;
    public BigDecimal pfl2;
    public BigDecimal pfl3;
    public BigDecimal pf;
    public BigDecimal totalactiveenergyimporttariff1;
}
