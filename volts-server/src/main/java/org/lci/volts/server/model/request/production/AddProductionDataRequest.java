package org.lci.volts.server.model.request.production;

import java.math.BigDecimal;

public record AddProductionDataRequest(String companyName,String productionName, BigDecimal value) {
}
