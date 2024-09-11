package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateGasRequest(@JsonProperty("gas_name") String name,
                               @JsonProperty("gas_name_new") String nameNew,
                               @JsonProperty("gas_description") String description,
                               @JsonProperty("company_name") String companyName) {
}
