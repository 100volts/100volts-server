package org.lci.volts.server.model.responce.gas;

import org.lci.volts.server.model.dto.gas.MonthlyGasMeterEnergyDTO;

import java.util.List;

public record GetGasSixMonthDataResponse(List<MonthlyGasMeterEnergyDTO> data) {
}
