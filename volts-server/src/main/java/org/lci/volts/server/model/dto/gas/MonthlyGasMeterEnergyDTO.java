package org.lci.volts.server.model.dto.gas;

import java.math.BigDecimal;
import java.time.Month;

public record MonthlyGasMeterEnergyDTO(Month month, BigDecimal value,BigDecimal valueTariff) {
}
