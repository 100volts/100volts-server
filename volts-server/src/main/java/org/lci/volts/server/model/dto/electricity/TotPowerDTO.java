package org.lci.volts.server.model.dto.electricity;

import java.math.BigDecimal;

public record TotPowerDTO(BigDecimal totPower, String timeStamp) {
}
