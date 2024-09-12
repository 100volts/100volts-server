package org.lci.volts.server.model.dto.water;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public record WaterDTO(String name, String description, String date, BigDecimal value, WaterDataDTO data) {
}
