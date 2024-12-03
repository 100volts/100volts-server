package org.lci.volts.server.model.record;

import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.electric.ElectricMeterData;

public record ElDataStartEnd(ElectricMeter meter, ElectricMeterData start, ElectricMeterData end) {
}
