package org.lci.volts.server.model.dto.electricity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ElMeterWithDataDTO{
    private int id;
    private String name;
    private String value;
    private String max;
    private String lastUpdated;
    private String month;
}
