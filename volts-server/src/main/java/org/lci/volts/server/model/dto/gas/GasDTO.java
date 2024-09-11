package org.lci.volts.server.model.dto.gas;


import java.util.List;

public record GasDTO(String name, String description, String date, List<GasDataDTO> data) {
}
