package org.lci.volts.server.model.request.production;

import java.math.BigDecimal;

public record AddProductionDataRequest(String productionName, BigDecimal value) {
}
