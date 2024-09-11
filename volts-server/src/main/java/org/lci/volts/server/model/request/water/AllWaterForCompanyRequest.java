package org.lci.volts.server.model.request.water;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AllWaterForCompanyRequest(@JsonProperty("company_name") String companyName) {
}
