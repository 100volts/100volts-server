package org.lci.volts.server.model.dto.settings;

public record ElMeterSettings(int address, String lastTimeRead, int timeGapRead) {
}
