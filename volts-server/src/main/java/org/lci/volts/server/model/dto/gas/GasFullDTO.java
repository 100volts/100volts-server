package org.lci.volts.server.model.dto.gas;

import java.math.BigDecimal;
import java.util.List;

public record GasFullDTO(String name, String description, String date, BigDecimal value, List<GasDataDTO> data) {
}
