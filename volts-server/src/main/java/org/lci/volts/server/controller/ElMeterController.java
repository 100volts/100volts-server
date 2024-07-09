package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.AuthenticationRequest;
import org.lci.volts.server.model.request.ElMeterReadRequest;
import org.lci.volts.server.model.responce.ElMeterReadResponse;
import org.lci.volts.server.service.ElMerterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/elmeter")
@RequiredArgsConstructor
public class ElMeterController {
    private final ElMerterService elMerterService;

    @PostMapping("/authenticate")
    public ResponseEntity<ElMeterReadResponse> authenticate(
            @RequestBody ElMeterReadRequest request,
            @RequestHeader("Host") String remoteAddress
    ) {
        return ResponseEntity.ok(elMerterService.setReadData(request.getElMeterData()));
    }

}
