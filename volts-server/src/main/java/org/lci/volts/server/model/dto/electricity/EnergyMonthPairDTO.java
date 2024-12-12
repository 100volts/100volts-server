package org.lci.volts.server.model.dto.electricity;

import java.math.BigDecimal;

public record EnergyMonthPairDTO(String month, BigDecimal energy,String dateFrom, String dateTo) {
}
