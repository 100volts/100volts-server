package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.ElMeterDTO;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.request.*;
import org.lci.volts.server.model.responce.*;
import org.lci.volts.server.service.ElMeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("elmeter")
@RequiredArgsConstructor
public class ElMeterController {
    private final ElMeterService elMeterService;

    @PostMapping("/data")
    public ResponseEntity<ElMeterReadResponse> insertData(
            @RequestBody ElMeterDataDTO request
    ) {
        return ResponseEntity.ok(elMeterService.setReadData(request));
    }

    @PostMapping("create")
    public ResponseEntity<ElMetterCreateResponse> createElectricMeter(
            @RequestBody ElMeterCreationRequest request
    ) {
        return ResponseEntity.ok(new ElMetterCreateResponse(elMeterService.createElMeter(new ElMeterDTO(
                request.getCompanyId(), request.getMeterAddress(), request.getMeterName()))));
    }

    @PostMapping
    public ResponseEntity<GetElMeterResponse> getElectricMeter(@RequestBody GetElMeterRequest request) {
        return ResponseEntity.ok(elMeterService.getElectricMeter(request.getAddress()));
    }

    @PostMapping("company/address/list")
    public ResponseEntity<GetAddressListElMeterResponse> getAddressListElectricMeterForCompany(@RequestBody GetAddressListElMeterRequest request) {
        return ResponseEntity.ok(elMeterService.getAddressListElectricMeterForCompany(request.getCompanyName()));
    }

    @PostMapping("company/address/list/names")
    public ResponseEntity<GetAddListAndElMeterNamesResponse> getAddressListWithNameElectricMeterForCompany(@RequestBody GetAddressListElMeterRequest request) {
        return ResponseEntity.ok(elMeterService.getAddressListWithNamesElectricMeterForCompany(request.getCompanyName()));
    }

    @PostMapping("/data/last")
    public ResponseEntity<GetElMeterAndDataResponse> getElectricMeterWithLastData(@RequestBody GetElMeterLastDataRequest request) {
        return ResponseEntity.ok(elMeterService.getElectricMeterWithLastData(request.getAddress(),request.getCompanyName()));
    }

    @PostMapping("/data/report")
    public ResponseEntity<GetElmeterReportResponse> getElmeterReportResponseResponseEntity(@RequestBody GetElmeterReportRequest request){
        return ResponseEntity.ok(elMeterService.getElmeterReportResponseResponseEntity(request));
    }

}
