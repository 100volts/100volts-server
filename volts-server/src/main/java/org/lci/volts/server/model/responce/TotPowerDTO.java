package org.lci.volts.server.model.responce;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public record TotPowerDTO(BigDecimal totPower, String timeStamp) {
}
