package org.lci.volts.server.model.request;

import lombok.Data;
import org.lci.volts.server.model.ElMeterDataDTO;

@Data
public class ElMeterReadRequest {
    private String key;
    private ElMeterDataDTO elMeterData;
}
