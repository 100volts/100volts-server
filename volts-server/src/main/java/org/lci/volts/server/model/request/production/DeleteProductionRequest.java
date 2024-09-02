package org.lci.volts.server.model.request.production;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteProductionRequest(@JsonProperty("production_name")String prodName,@JsonProperty("company_name") String companyName) {
}
