package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record DeleteGasRequest(@JsonProperty("gas_name") String name, @JsonProperty("company_name") String companyName,
                               BigDecimal value) {
}
