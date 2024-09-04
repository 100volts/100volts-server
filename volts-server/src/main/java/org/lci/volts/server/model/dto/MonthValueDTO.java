package org.lci.volts.server.model.dto;

import java.math.BigDecimal;
import java.time.Month;

public record MonthValueDTO(Month month, BigDecimal value) {
}
