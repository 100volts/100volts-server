package org.lci.volts.server.model.responce.electric.settings;

import org.lci.volts.server.model.dto.settings.ElMeterSettings;

import java.sql.Date;
import java.util.List;

public record ElMeterSettingsForCompanyResponse(List<ElMeterSettings> settings) {
}
