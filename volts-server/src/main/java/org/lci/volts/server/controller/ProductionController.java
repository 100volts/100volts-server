package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.production.GetProductionRequest;
import org.lci.volts.server.model.responce.production.GetProductionResponse;
import org.lci.volts.server.persistence.Production;
import org.lci.volts.server.service.ProductionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("production")
@RequiredArgsConstructor
public class ProductionController {
    private final ProductionService productionService;

    @PostMapping
    public ResponseEntity<GetProductionResponse> getProductionByName(@RequestBody final GetProductionRequest request) {
        return ResponseEntity.ok(productionService.getProdByName(request));
    }
}
