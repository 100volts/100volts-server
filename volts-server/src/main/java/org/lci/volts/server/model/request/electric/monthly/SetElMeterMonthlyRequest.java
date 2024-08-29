package org.lci.volts.server.model.request.electric.monthly;

import java.math.BigDecimal;
import java.sql.Date;

public record SetElMeterMonthlyRequest(Long id,BigDecimal tariff1,BigDecimal tarif2, Date tz) {
}
