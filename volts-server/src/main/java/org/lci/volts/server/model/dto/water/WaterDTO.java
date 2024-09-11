package org.lci.volts.server.model.dto.water;

import java.util.List;

public record WaterDTO(String name, String description, String date, List<WaterDataDTO> data) {
}
