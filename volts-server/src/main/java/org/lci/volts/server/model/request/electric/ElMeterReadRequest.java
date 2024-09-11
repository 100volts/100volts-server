package org.lci.volts.server.model.request.electric;

import lombok.Data;
import org.lci.volts.server.model.dto.electricity.ElMeterDataDTO;

@Data
public class ElMeterReadRequest {
    private String key;
    private ElMeterDataDTO elMeterData;
}
