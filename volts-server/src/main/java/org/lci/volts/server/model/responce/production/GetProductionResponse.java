package org.lci.volts.server.model.responce.production;

import org.lci.volts.server.model.dto.MonthValueDTO;
import org.lci.volts.server.model.dto.ProductionDTO;

import java.util.List;

public record GetProductionResponse(ProductionDTO production,
                                    List<MonthValueDTO> groupedByMonth){
}
