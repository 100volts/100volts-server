package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record AllGasForCompanyRequest(
        @JsonProperty("company_name") String companyName,
        @JsonProperty("sum_value") BigDecimal sumValues,
        @JsonProperty("meter_names") List<String> gasMeterNames
) {
}
