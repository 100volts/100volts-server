package org.lci.volts.server.model.responce.production;

import java.util.List;

public record ProductionGroupResponse(List<String> prodGroupNames,List<String> elMeterNames) {
}
