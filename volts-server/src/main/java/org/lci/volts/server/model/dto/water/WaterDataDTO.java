package org.lci.volts.server.model.dto.water;

import java.math.BigDecimal;

public record WaterDataDTO(BigDecimal value, String date) {
}
