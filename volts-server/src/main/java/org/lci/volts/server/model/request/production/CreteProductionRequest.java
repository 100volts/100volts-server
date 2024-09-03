package org.lci.volts.server.model.request.production;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreteProductionRequest(@JsonProperty("production_name")String prodName,@JsonProperty("production_description") String prodDescription,
                                     @JsonProperty("company_name") String companyName,@JsonProperty("units_name") String unitsName,
                                     @JsonProperty("group_name") String groupName,@JsonProperty("el_name") String[] elMeterNames ) {
}
