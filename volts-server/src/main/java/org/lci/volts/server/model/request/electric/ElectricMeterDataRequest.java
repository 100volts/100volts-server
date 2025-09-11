package org.lci.volts.server.model.request.electric;

import java.time.OffsetDateTime;

public record ElectricMeterDataRequest(
        int meterId,
        String name,
        double energyActiveImport,
        double energyActiveExport,
        double energyReactiveImport,
        double energyReactiveExport,
        double energyApparent,
        OffsetDateTime recordedAt
) {}
