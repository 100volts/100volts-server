package org.lci.volts.server.model.dto.electricity;
import java.math.BigDecimal;
import java.time.DayOfWeek;


public record DailyElMeterEnergyDTO(String date, DayOfWeek day, BigDecimal energy) {
}
