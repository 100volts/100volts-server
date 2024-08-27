package org.lci.volts.server.model.responce;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;

public record GetElectricMeterDailyTotPowerResponse(List<TotPowerDTO> dailyTariff) {
}
