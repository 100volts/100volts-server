package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.error.ControllerErrorReportRequest;
import org.lci.volts.server.service.ConnectorReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("connector/report")
@RequiredArgsConstructor
public class ConnectorReportsController {
    private final ConnectorReportsService connectorReportsService;
    @PostMapping
    public ResponseEntity<Boolean> reportError(@RequestBody ControllerErrorReportRequest request){
        return ResponseEntity.ok(connectorReportsService.writeReport(request));
    }
}
