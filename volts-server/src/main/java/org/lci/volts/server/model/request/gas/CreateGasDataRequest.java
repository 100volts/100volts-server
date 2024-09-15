package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateGasDataRequest(@JsonProperty("gas_meter_name") String gasMeterName, LocalDateTime date,
                                   @JsonProperty("company_name") String companyName, BigDecimal value) {
}
