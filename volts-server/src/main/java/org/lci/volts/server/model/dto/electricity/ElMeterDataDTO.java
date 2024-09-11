package org.lci.volts.server.model.dto.electricity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElMeterDataDTO {
    public BigDecimal merterId;
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
    public BigDecimal totalActivePpower;
    public BigDecimal totalActiveEnergyImportTariff1;
    public BigDecimal totalActiveEnergyImportTariff2;
}
