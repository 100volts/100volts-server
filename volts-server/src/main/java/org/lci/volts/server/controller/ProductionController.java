package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.production.*;
import org.lci.volts.server.model.responce.production.*;
import org.lci.volts.server.service.ProductionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("production")
@RequiredArgsConstructor
public class ProductionController {
    private final ProductionService productionService;

    @PostMapping("/company")
    public ResponseEntity<GetProductionResponse> getProductionByName(@RequestBody final GetProductionRequest request) {
        return ResponseEntity.ok(productionService.getProdByName(request));
    }
    @PutMapping("/company")
    public ResponseEntity<UpdateProductionResponse> updateProductionByName(@RequestBody final UpdateProductionRequest request) {
        return ResponseEntity.ok(productionService.updateProd(request));
    }
    @DeleteMapping
    public ResponseEntity<DeleteProductionResponse> deleteProductionByName(@RequestBody final DeleteProductionRequest request) {
        return ResponseEntity.ok(productionService.deleteProductionByName(request));
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

    @PostMapping("/company/group")
    public ResponseEntity<ProductionGroupResponse> getProductionGroup(@RequestBody final ProductionGroupRequest request) {
        return ResponseEntity.ok(productionService.getProductionGroup(request));
    }

    @PostMapping("/company/data/report")
    public ResponseEntity<ProductionDataReportResponse> getProductionDataReport(@RequestBody final ProductionDataReportRequest request) {
        return ResponseEntity.ok(productionService.getProductionDataReport(request));
    }

    @PostMapping("/company/data/pack")
    public ResponseEntity<GetProductionDataPackResponse> getProductionDataPack(@RequestBody final GetProductionDataPackRequest request) {
        return ResponseEntity.ok(productionService.getProductionDataPack(request));
    }

    @DeleteMapping("/data")
    public ResponseEntity<DeleteProductionDataResponse> deleteProductionData(@RequestBody final DeleteProductionDataRequest request){
        return ResponseEntity.ok(new DeleteProductionDataResponse(productionService.deleteProductionDate(request)));
    }
}
