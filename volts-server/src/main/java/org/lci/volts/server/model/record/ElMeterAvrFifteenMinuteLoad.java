package org.lci.volts.server.model.record;

import java.math.BigDecimal;

public record ElMeterAvrFifteenMinuteLoad(BigDecimal voltage,BigDecimal current,BigDecimal power, BigDecimal powerFactor) {
}
