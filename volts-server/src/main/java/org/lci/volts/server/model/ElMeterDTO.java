package org.lci.volts.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElMeterDTO {
    private int companyId;
    private int meterAddress;
    private String meterName;

}
