package org.lci.volts.server.model.request.kpi;

import org.lci.volts.server.model.dto.SettingsDTO;
import org.lci.volts.server.model.dto.energy.EnergyDTO;
import org.lci.volts.server.model.dto.kpi.KPIGroupDTO;

import java.util.List;

public record KPIUpdateRequest(String KPIName,String newName, String description, String company, String target, KPIGroupDTO group,
                               EnergyDTO energy, SettingsDTO settings, List<String> prodNames) {
}
