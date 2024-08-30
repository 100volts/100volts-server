package org.lci.volts.server.model.request.production;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetProductionRequest(String name, @JsonProperty("company_name") String companyName) {
}
