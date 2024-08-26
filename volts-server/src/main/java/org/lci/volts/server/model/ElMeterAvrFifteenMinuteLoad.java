package org.lci.volts.server.model;

import java.math.BigDecimal;

public record ElMeterAvrFifteenMinuteLoad(BigDecimal voltage,BigDecimal current,BigDecimal power, BigDecimal powerFactor) {
}
