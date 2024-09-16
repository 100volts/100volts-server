package org.lci.volts.server.model.request.water;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WaterReportRequest(@JsonProperty("water_name") String name, @JsonProperty("company_name") String companyName) {
}
