package org.lci.volts.server.model.request.gas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public record MonthlyGasRequest(@JsonProperty("company_name") String companyName, Date from, Date to) {
}
