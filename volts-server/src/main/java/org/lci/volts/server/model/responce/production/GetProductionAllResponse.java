package org.lci.volts.server.model.responce.production;

import org.lci.volts.server.model.dto.production.ProductionPackageDTO;

import java.util.List;

public record GetProductionAllResponse(List<ProductionPackageDTO> production) {
}
