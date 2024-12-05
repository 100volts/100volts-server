package org.lci.volts.server.model.record;

import org.lci.volts.server.persistence.production.Production;

import java.util.List;

public record ProdWhitStartEndData(Production prod, List<ElDataStartEnd> startEndData) {
}
