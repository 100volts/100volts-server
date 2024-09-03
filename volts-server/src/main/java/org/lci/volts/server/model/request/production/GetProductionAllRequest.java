package org.lci.volts.server.model.request.production;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetProductionAllRequest(@JsonProperty("company_name") String companyName) {
}
