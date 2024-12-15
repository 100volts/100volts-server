package org.lci.volts.server.model.dto.production;

import java.math.BigDecimal;

public record ProductionDataForKpiDTO(Long id, BigDecimal values, String date, ProductionDTO prod) {
}
