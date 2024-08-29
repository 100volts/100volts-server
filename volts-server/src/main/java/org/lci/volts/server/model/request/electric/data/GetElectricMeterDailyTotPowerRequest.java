package org.lci.volts.server.model.request.electric.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetElectricMeterDailyTotPowerRequest {
    private int address;
    @JsonProperty("company_name")
    private String companyName;
    private String tz;
}
