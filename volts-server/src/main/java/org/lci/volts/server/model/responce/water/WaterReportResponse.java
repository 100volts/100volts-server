package org.lci.volts.server.model.responce.water;

import org.lci.volts.server.model.dto.water.WaterDataDTO;

import java.util.List;

public record WaterReportResponse(List<WaterDataDTO> data) {
}
