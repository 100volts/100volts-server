package org.lci.volts.server.model.dto;

import java.math.BigDecimal;
import java.time.Month;
import java.time.DayOfWeek;

public record MonthValueDTO(Month month, BigDecimal value) {
}
