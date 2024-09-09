package org.lci.volts.server.model.dto;
import java.math.BigDecimal;
import java.time.DayOfWeek;


public record DailyElMeterEnergyDTO(String date, DayOfWeek day, BigDecimal energy) {
}
