package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.production.GetProductionRequest;
import org.lci.volts.server.model.responce.production.GetProductionResponse;
import org.lci.volts.server.service.KPIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kpi/production")
@RequiredArgsConstructor
public class KPIProduction {
    private final KPIService kpiService;

    @PutMapping
    public ResponseEntity<String> putProductionKPI(@RequestBody final GetProductionRequest request) {
        return ResponseEntity.ok("ok");
    }
}
