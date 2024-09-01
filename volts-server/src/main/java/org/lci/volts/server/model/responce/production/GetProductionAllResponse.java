package org.lci.volts.server.model.responce.production;

import org.lci.volts.server.model.dto.ProductionDTO;

import java.util.List;

public record GetProductionAllResponse(List<ProductionDTO> production) {
}
