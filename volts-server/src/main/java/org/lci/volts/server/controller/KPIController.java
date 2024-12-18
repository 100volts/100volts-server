package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.kpi.KPICreateRequest;
import org.lci.volts.server.model.request.kpi.KPIDeleteRequest;
import org.lci.volts.server.model.request.kpi.KPIPayloadRequest;
import org.lci.volts.server.model.request.kpi.KPIUpdateByDateRequest;
import org.lci.volts.server.model.responce.kpi.KPICreateResponse;
import org.lci.volts.server.model.responce.kpi.KPIPayloadResponse;
import org.lci.volts.server.model.responce.kpi.KPIUpdateByDateResponse;
import org.lci.volts.server.service.KPIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/create")
    public ResponseEntity<KPICreateResponse> create(@RequestBody KPICreateRequest request){
        return ResponseEntity.ok(new KPICreateResponse(kpiService.createKPI(request)));
    }
    @PutMapping
    public ResponseEntity<KPICreateResponse> update(@RequestBody KPICreateRequest request){
        return ResponseEntity.ok(new KPICreateResponse(kpiService.updateKPI(request)));
    }
    @PostMapping("/update")
    public ResponseEntity<KPIUpdateByDateResponse> updateKpi(@RequestBody KPIUpdateByDateRequest request){
        return ResponseEntity.ok(kpiService.updateKpiData(request.company(),request.kpiName(), Date.valueOf(request.date())));
    }

    @DeleteMapping
    public ResponseEntity<KPICreateResponse> delete(@RequestBody KPIDeleteRequest request){
        return ResponseEntity.ok(new KPICreateResponse(kpiService.delete(request)));
    }

}
