package org.lci.volts.server.model.responce.production;

import org.lci.volts.server.model.dto.ProductionDataDTO;

import java.math.BigDecimal;
import java.util.List;

public record GetProductionDataPackResponse(List<ProductionDataDTO> last10) {
}
