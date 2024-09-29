package org.lci.volts.server.model.request.error;

import com.fasterxml.jackson.annotation.JsonProperty;

    public record ControllerErrorReportRequest(@JsonProperty("company_name") String companyName, String content,@JsonProperty("time_stamp") String timeStamp) {
}
