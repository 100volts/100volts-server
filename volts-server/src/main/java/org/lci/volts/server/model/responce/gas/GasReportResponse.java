package org.lci.volts.server.model.responce.gas;

import org.lci.volts.server.model.dto.gas.GasDataDTO;

import java.util.List;

public record GasReportResponse(List<GasDataDTO> data) {
}
