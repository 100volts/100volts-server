package org.lci.volts.server.model.responce.electric.data;

import org.lci.volts.server.model.dto.electricity.TotPowerDTO;

import java.util.List;

public record GetElectricMeterDailyTotPowerResponse(List<TotPowerDTO> dailyTariff) {
}
