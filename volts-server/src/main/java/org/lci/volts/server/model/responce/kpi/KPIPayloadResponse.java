package org.lci.volts.server.model.responce.kpi;

import org.lci.volts.server.model.dto.kpi.KPIDTO;

import java.util.List;

public record KPIPayloadResponse(List<KPIDTO> KPIData) {
}
