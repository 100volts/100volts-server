package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.electric.settings.ElectricMeterSettingRequest;
import org.lci.volts.server.model.responce.electric.settings.ElectricMeterSettingResponse;
import org.lci.volts.server.service.ElMeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("elmeterc")
@RequiredArgsConstructor
public class ElectricMeterRemoteController {
    private final ElMeterService elMeterService;

    @PostMapping("/settings/controller")
    public ResponseEntity<ElectricMeterSettingResponse> readSetting(@RequestBody ElectricMeterSettingRequest request){
        return ResponseEntity.ok(elMeterService.getSettings(request));
    }
}
