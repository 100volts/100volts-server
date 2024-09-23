package org.lci.volts.server.model.request.electric.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ElMeterSettingsForCompanyRequest(@JsonProperty("company_name") String companyName) {
}
