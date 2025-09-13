package org.lci.volts.server.model.responce.controller;

import java.util.List;
import java.util.Map;

public record CollectControllerTimeSheetIfChangedResponse(
        List<Object> timeSheet, String status
) {
}
