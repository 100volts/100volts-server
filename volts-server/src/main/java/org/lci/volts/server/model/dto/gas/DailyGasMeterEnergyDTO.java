package org.lci.volts.server.model.dto.gas;

import java.math.BigDecimal;
import java.time.DayOfWeek;

public record DailyGasMeterEnergyDTO(String date, DayOfWeek day, BigDecimal value) {
}
