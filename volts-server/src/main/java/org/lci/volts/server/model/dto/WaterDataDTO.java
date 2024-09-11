package org.lci.volts.server.model.dto;

import java.math.BigDecimal;

public record WaterDataDTO(BigDecimal value, String date) {
}
