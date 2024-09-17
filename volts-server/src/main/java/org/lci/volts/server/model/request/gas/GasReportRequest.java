package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GasReportRequest(@JsonProperty("gas_name") String name, @JsonProperty("company_name") String companyName) {
}
