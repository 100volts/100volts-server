package org.lci.volts.server.model.responce.electric.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetElMeterResponse {
    private String name;
    private int address;
}
