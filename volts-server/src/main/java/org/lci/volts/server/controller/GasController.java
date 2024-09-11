package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.gas.AllGasForCompanyRequest;
import org.lci.volts.server.model.request.gas.CreateGasDataRequest;
import org.lci.volts.server.model.request.gas.CreateGasRequest;
import org.lci.volts.server.model.responce.gas.AllGasForCompanyResponse;
import org.lci.volts.server.service.GasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gas")
@RequiredArgsConstructor
public class GasController {
    private final GasService gasService;
    @PostMapping("/all")
    public ResponseEntity<AllGasForCompanyResponse> allGasForCompany(@RequestBody AllGasForCompanyRequest request){
        return ResponseEntity.ok(gasService.getAllGasForCompany(request.companyName()));
    }
    @PutMapping
    public ResponseEntity<Boolean> createGas(@RequestBody CreateGasRequest request){
        return ResponseEntity.ok(gasService.createUpdateGasRequest(request));
    }
    @PutMapping("/data")
    public ResponseEntity<Boolean> puGasData(@RequestBody CreateGasDataRequest request){
        return ResponseEntity.ok(gasService.addGasDateRequest(request));
    }
}
