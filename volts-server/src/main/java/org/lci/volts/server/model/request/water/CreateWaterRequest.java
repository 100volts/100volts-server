package org.lci.volts.server.model.request.water;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateWaterRequest(@JsonProperty("water_name") String name,@JsonProperty("water_name_new") String nameNew,@JsonProperty("water_description")String description, @JsonProperty("company_name") String companyName) {
}
