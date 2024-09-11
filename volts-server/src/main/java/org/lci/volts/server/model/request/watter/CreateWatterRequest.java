package org.lci.volts.server.model.request.watter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateWatterRequest(@JsonProperty("watter_name") String name,@JsonProperty("watter_name_new") String nameNew,@JsonProperty("watter_description")String description, @JsonProperty("company_name") String companyName) {
}
