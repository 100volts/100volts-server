package org.lci.volts.server.model.responce.production;

import org.lci.volts.server.model.dto.electricity.MonthValueDTO;
import org.lci.volts.server.model.dto.production.ProductionDTO;

import java.util.List;

public record GetProductionResponse(ProductionDTO production,
                                    List<MonthValueDTO> groupedByMonth){
}
