package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.service.KPIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kpi")
@RequiredArgsConstructor
public class KPIController {
    private final KPIService kpiService;
    @PostMapping("/all")
    public ResponseEntity<Object> allForCompany(@RequestBody Object request){
        return ResponseEntity.ok(kpiService.getAllFromCompany(request));
    }

}
