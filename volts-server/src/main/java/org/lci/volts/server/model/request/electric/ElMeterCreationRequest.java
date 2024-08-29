package org.lci.volts.server.model.request.electric;

import lombok.Data;

@Data
public class ElMeterCreationRequest {
    private int companyId;
    private int meterAddress;
    private String meterName;
}
