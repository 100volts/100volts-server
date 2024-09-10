package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.watter.CreateWatterRequest;
import org.lci.volts.server.model.responce.watter.AllWatterForCompanyResponse;
import org.lci.volts.server.service.WatterService;
import org.lci.volts.server.model.request.watter.AllWatterForCompanyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("watter")
@RequiredArgsConstructor
public class WatterController {
    private final WatterService watterService;
    @PostMapping("/all")
    public ResponseEntity<AllWatterForCompanyResponse> allWatterForCompany(@RequestBody AllWatterForCompanyRequest request){
        return ResponseEntity.ok(watterService.getAllWatterForCompany(request.companyName()));
    }
    @PutMapping
    public ResponseEntity<Boolean> createWatter(@RequestBody CreateWatterRequest request){
        return ResponseEntity.ok(watterService.createWatterRequest(request));
    }
}
