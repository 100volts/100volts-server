package org.lci.volts.server.model.responce;

import org.lci.volts.server.model.ElMeterDataDTO;

import java.util.List;

public record GetElmeterReportResponse(List<List<ElMeterDataDTO>> meters) {
}
