package org.lci.volts.server.model.request.production;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductionDataReportRequest(@JsonProperty("company_name") String companyName, @JsonProperty("production_name") String productionName) {
}
