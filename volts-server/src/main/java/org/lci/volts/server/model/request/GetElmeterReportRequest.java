package org.lci.volts.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetElmeterReportRequest(int address, @JsonProperty("company_name") String companyName, int pages,@JsonProperty("page_limit") int pageLimit ) {
}
