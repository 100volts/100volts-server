package org.lci.volts.server.model.request.electric;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ElMeterEnergyRequest(@JsonProperty("company_name") String companyName) {
}
