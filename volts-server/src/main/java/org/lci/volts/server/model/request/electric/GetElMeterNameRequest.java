package org.lci.volts.server.model.request.electric;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetElMeterNameRequest(@JsonProperty("company_name") String companyName) {
}
