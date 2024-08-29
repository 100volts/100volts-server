package org.lci.volts.server.model.request;

public record GetElmeterReportRequest(int address, String companyName, int pages, int pageLimit ) {
}
