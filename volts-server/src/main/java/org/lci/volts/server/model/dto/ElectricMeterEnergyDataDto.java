package org.lci.volts.server.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ElectricMeterEnergyDataDto(
        int meterId,
        BigDecimal energyActiveImport,
        BigDecimal energyActiveExport,
        BigDecimal energyReactiveImport,
        BigDecimal energyReactiveExport,
        BigDecimal energyApparent,
        String recordedAt
) {
}
