package org.lci.volts.server.model.responce.electric;

import org.lci.volts.server.model.dto.ElMeterDataDTO;

import java.util.List;

public record GetElmeterReportResponse(List<List<ElMeterDataDTO>> meters) {
}