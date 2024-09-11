package org.lci.volts.server.model.responce.water;

import org.lci.volts.server.model.dto.WaterDTO;

import java.util.List;

public record AllWaterForCompanyResponse(List<WaterDTO> watter) {
}
