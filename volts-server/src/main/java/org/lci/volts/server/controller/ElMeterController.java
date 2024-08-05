package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.ElMeterDTO;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.request.ElMeterCreationRequest;
import org.lci.volts.server.model.request.ElMeterReadRequest;
import org.lci.volts.server.model.responce.ElMeterReadResponse;
import org.lci.volts.server.model.responce.ElMetterCreateResponse;
import org.lci.volts.server.service.ElMerterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("elmeter")
@RequiredArgsConstructor
public class ElMeterController {
    private final ElMerterService elMerterService;

    @PostMapping("/data")
    public ResponseEntity<ElMeterReadResponse> insertData(
            @RequestBody ElMeterDataDTO request
    ) {
        return ResponseEntity.ok(elMerterService.setReadData(request));
    }

    @PostMapping("create")
    public ResponseEntity<ElMetterCreateResponse> createElectricMeter(
            @RequestBody ElMeterCreationRequest request
    ) {
        return ResponseEntity.ok(new ElMetterCreateResponse(elMerterService.createElMeter(new ElMeterDTO(
                request.getCompanyId(), request.getMeterAddress(), request.getMeterName()))));
    }

}
