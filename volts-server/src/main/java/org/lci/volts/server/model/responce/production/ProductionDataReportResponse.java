package org.lci.volts.server.model.responce.production;

import org.lci.volts.server.model.dto.production.ProductionDataDTO;

import java.util.List;

public record ProductionDataReportResponse(List<ProductionDataDTO> productionData) {
}
