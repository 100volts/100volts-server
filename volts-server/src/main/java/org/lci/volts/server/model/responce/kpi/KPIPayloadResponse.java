package org.lci.volts.server.model.responce.kpi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lci.volts.server.model.dto.kpi.KPIDTO;
import org.lci.volts.server.model.dto.kpi.KPIGroupDTO;

import java.util.List;

public record KPIPayloadResponse(List<KPIDTO> KPIData,@JsonProperty("all_groups") List<KPIGroupDTO> allGroups) {
}
