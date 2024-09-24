package org.lci.volts.server.model.dto.settings;

public record ElMeterSettings(int address,String name, String lastTimeRead, int timeGapRead) {
}
