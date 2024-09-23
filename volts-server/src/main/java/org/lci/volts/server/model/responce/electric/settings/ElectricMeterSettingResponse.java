package org.lci.volts.server.model.responce.electric.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ElectricMeterSettingResponse(int address, @JsonProperty("read_time_gap") int readTimeGap) {
}
