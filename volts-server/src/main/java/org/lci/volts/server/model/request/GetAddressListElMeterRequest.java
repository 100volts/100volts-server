package org.lci.volts.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetAddressListElMeterRequest {
    @JsonProperty("company_name")
    private String companyName;
}
