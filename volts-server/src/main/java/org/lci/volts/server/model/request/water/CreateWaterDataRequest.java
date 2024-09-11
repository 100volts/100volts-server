package org.lci.volts.server.model.request.water;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CreateWaterDataRequest(@JsonProperty("water_meter_name") String waterMeterName,
                                     @JsonProperty("company_name") String companyName, BigDecimal value) {
}
