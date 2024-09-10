package org.lci.volts.server.model.dto;

import java.util.List;

public record WatterDTO(String name, String description, String date, List<WatterDataDTO> data) {
}
