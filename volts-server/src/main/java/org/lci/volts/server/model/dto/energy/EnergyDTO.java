package org.lci.volts.server.model.dto.energy;

import org.lci.volts.server.model.dto.electricity.ElMeterDTO;

import java.util.List;

public record EnergyDTO(String index, List<String> electricEnergyName ) {
}
