package org.lci.volts.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAddListAndElMeterNamesDTO {
    private String name;
    private int address;
}
