package org.lci.volts.server.model.dto.electricity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElMeterDTO {
    private int companyId;
    private int meterAddress;
    private String meterName;

}
