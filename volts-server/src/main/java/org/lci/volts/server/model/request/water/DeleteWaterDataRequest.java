package org.lci.volts.server.model.request.water;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public record DeleteWaterDataRequest(@JsonProperty("water_name") String name,
                                     @JsonProperty("company_name") String companyName, BigDecimal value) {
}
