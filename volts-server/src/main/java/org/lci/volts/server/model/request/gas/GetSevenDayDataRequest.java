package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetSevenDayDataRequest(@JsonProperty("gas_meter_name") String gasMeterName,
                                     @JsonProperty("company_name") String companyName) {
}
