package org.lci.volts.server.model.dto.kpi;

import org.lci.volts.server.model.dto.production.ProductionDataForKpiDTO;

import java.util.List;

public record KPIDataDTO(float value, String ts, List<ProductionDataForKpiDTO> productionDataDTO) {
}
