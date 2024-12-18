package org.lci.volts.server.model.request.production;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Date;

public record AddProductionDataRequest(
        @JsonProperty("company_name") String companyName,
        @JsonProperty("production_name") String productionName,
        BigDecimal value,
        Date date) {
}
