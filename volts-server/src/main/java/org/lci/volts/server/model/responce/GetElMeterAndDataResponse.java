package org.lci.volts.server.model.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.lci.volts.server.model.ElMeterDataDTO;

@Data
@AllArgsConstructor
public class GetElMeterAndDataResponse {
    private String name;
    private int address;
    @JsonProperty("electric_meter_data")
    private ElMeterDataDTO data;
}
