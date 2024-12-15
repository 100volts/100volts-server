package org.lci.volts.server.model.dto.kpi;

import org.lci.volts.server.model.dto.EnergyDTO;
import org.lci.volts.server.model.dto.production.ProductionDTO;

import java.util.List;

public record KPIDTO(String name, String description, String group, String currentTarget, EnergyDTO energy, List<ProductionDTO> productionDTO, String ts,List<KPIDataDTO> kpiDataDTOS) {
}
