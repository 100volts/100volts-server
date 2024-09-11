package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AllGasForCompanyRequest(@JsonProperty("company_name") String companyName) {
}
