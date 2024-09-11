package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CreateGasDataRequest(@JsonProperty("gas_meter_name") String gasMeterName,
                                   @JsonProperty("company_name") String companyName, BigDecimal value) {
}
