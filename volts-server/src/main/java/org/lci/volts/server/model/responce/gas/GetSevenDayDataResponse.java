package org.lci.volts.server.model.responce.gas;

import org.lci.volts.server.model.dto.gas.DailyGasMeterEnergyDTO;

import java.util.List;

public record GetSevenDayDataResponse(List<DailyGasMeterEnergyDTO> data) {
}
