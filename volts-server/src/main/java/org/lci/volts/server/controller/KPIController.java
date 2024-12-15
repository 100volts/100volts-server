package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.kpi.KPIPayloadRequest;
import org.lci.volts.server.model.request.kpi.KPIUpdateByDateRequest;
import org.lci.volts.server.model.responce.kpi.KPIPayloadResponse;
import org.lci.volts.server.model.responce.kpi.KPIUpdateByDateResponse;
import org.lci.volts.server.service.KPIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("kpi")
@RequiredArgsConstructor
public class KPIController {
    private final KPIService kpiService;
    @PostMapping("/all")
    public ResponseEntity<KPIPayloadResponse> allForCompany(@RequestBody KPIPayloadRequest request){
        return ResponseEntity.ok(kpiService.getAllFromCompany(request));
    }
    @PostMapping("/update")
    public ResponseEntity<KPIUpdateByDateResponse> updateKpi(@RequestBody KPIUpdateByDateRequest request){
        return ResponseEntity.ok(kpiService.updateKpi(request.company(),request.kpiName(), Date.valueOf(request.date())));
    }

}
