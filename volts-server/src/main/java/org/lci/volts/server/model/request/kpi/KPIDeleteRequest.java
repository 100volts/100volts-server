package org.lci.volts.server.model.request.kpi;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KPIDeleteRequest(@JsonProperty("company") String companyName,@JsonProperty("kpi_name") String kpiName) {
}
