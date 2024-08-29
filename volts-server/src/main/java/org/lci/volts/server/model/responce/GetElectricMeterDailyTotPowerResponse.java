package org.lci.volts.server.model.responce;

import java.util.List;

public record GetElectricMeterDailyTotPowerResponse(List<TotPowerDTO> dailyTariff) {
}
