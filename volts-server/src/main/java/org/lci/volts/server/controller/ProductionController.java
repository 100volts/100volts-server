package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.production.AddProductionDataRequest;
import org.lci.volts.server.model.request.production.CreteProductionRequest;
import org.lci.volts.server.model.request.production.GetProductionAllRequest;
import org.lci.volts.server.model.request.production.GetProductionRequest;
import org.lci.volts.server.model.responce.production.AddProductionDataResponse;
import org.lci.volts.server.model.responce.production.CreteProductionResponse;
import org.lci.volts.server.model.responce.production.GetProductionAllResponse;
import org.lci.volts.server.model.responce.production.GetProductionResponse;
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

    @PostMapping("/company")
    public ResponseEntity<GetProductionResponse> getProductionByName(@RequestBody final GetProductionRequest request) {
        return ResponseEntity.ok(productionService.getProdByName(request));
    }
    @PostMapping("/company/all")
    public ResponseEntity<GetProductionAllResponse> getProductionByCompanyName(@RequestBody final GetProductionAllRequest request) {
        return ResponseEntity.ok(productionService.getProdAllByName(request));
    }
    @PostMapping("/company/create")
    public ResponseEntity<CreteProductionResponse> createProductionByName(@RequestBody final CreteProductionRequest request) {
        return ResponseEntity.ok(productionService.createProdByName(request));
    }

    @PostMapping("/company/data")
    public ResponseEntity<AddProductionDataResponse> addProductionData(@RequestBody final AddProductionDataRequest request) {
        return ResponseEntity.ok(productionService.addProductionData(request));
    }
}
