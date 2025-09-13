package org.lci.volts.server.model.request.controller;

import java.util.List;
import java.util.Map;

public record ControllerCreateUpdateRequest(String companyName, List<Object> timeSheet) {
}
