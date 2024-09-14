package org.lci.volts.server.model.dto.gas;


import java.math.BigDecimal;

public record GasDTO(String name, String description, String date, BigDecimal value, GasDataDTO data) {
}
