package org.lci.volts.server.model.responce.electric;

import org.lci.volts.server.model.dto.ElectricMeterEnergyDataDto;

import java.util.List;

public record ElMeterEnergyResponse(List<ElectricMeterEnergyDataDto> data) {
}
