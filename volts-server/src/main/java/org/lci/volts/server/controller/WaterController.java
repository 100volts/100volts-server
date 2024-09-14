package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.water.CreateWaterDataRequest;
import org.lci.volts.server.model.request.water.CreateWaterRequest;
import org.lci.volts.server.model.responce.water.AllWaterForCompanyResponse;
import org.lci.volts.server.service.WaterService;
import org.lci.volts.server.model.request.water.AllWaterForCompanyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("water")
@RequiredArgsConstructor
public class WaterController {
    private final WaterService waterService;
    @PostMapping("/all")
    public ResponseEntity<AllWaterForCompanyResponse> allWaterForCompany(@RequestBody AllWaterForCompanyRequest request){
        return ResponseEntity.ok(waterService.getAllWaterForCompany(request.companyName()));
    }
    @PutMapping
    public ResponseEntity<Boolean> createWater(@RequestBody CreateWaterRequest request){
        return ResponseEntity.ok(waterService.createUpdateWaterRequest(request));
    }
    @PutMapping("/data")
    public ResponseEntity<Boolean> puWaterData(@RequestBody CreateWaterDataRequest request){
        return ResponseEntity.ok(waterService.addWaterDateRequest(request));
    }
}