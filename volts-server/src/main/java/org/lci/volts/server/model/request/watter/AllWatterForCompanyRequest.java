package org.lci.volts.server.model.request.watter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AllWatterForCompanyRequest(@JsonProperty("company_name") String companyName) {
}
