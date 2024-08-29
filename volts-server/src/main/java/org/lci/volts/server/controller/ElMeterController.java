package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.ElMeterDTO;
import org.lci.volts.server.model.dto.ElMeterDataDTO;
import org.lci.volts.server.model.request.electric.ElMeterCreationRequest;
import org.lci.volts.server.model.request.electric.GetAddressListElMeterRequest;
import org.lci.volts.server.model.request.electric.GetElMeterRequest;
import org.lci.volts.server.model.request.electric.data.GetElMeterLastDataRequest;
import org.lci.volts.server.model.request.electric.data.GetElectricMeterDailyTotPowerRequest;
import org.lci.volts.server.model.request.electric.data.GetElmeterReportRequest;
import org.lci.volts.server.model.request.electric.monthly.SetElMeterMonthlyRequest;
import org.lci.volts.server.model.responce.electric.ElMetterCreateResponse;
import org.lci.volts.server.model.responce.electric.GetAddListAndElMeterNamesResponse;
import org.lci.volts.server.model.responce.electric.GetAddressListElMeterResponse;
import org.lci.volts.server.model.responce.electric.GetElmeterReportResponse;
import org.lci.volts.server.model.responce.electric.data.ElMeterReadResponse;
import org.lci.volts.server.model.responce.electric.data.GetElMeterAndDataResponse;
import org.lci.volts.server.model.responce.electric.data.GetElMeterResponse;
import org.lci.volts.server.model.responce.electric.data.GetElectricMeterDailyTotPowerResponse;
import org.lci.volts.server.model.responce.electric.monthly.SetElMeterMontlyResponse;
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

    @PostMapping("/monthly/set")
    public ResponseEntity<SetElMeterMontlyResponse> setElectricMeterMonthlyData(@RequestBody SetElMeterMonthlyRequest request) {
        return ResponseEntity.ok(elMeterService.setMonthlyReadData(request));
    }

    @PostMapping("/data/last")
    public ResponseEntity<GetElMeterAndDataResponse> getElectricMeterWithLastData(@RequestBody GetElMeterLastDataRequest request) {
        return ResponseEntity.ok(elMeterService.getElectricMeterWithLastData(request.getAddress(),request.getCompanyName()));
    }

    @PostMapping("/data/daily")
    public ResponseEntity<GetElectricMeterDailyTotPowerResponse> getElectricMeterDailyData(@RequestBody GetElectricMeterDailyTotPowerRequest request) {
        return ResponseEntity.ok(elMeterService.getDailyTotPowerTariff(request.getAddress(),request.getCompanyName()));
    }

    @PostMapping("/data/report")
    public ResponseEntity<GetElmeterReportResponse> getElmeterReportResponseResponseEntity(@RequestBody GetElmeterReportRequest request){
        return ResponseEntity.ok(elMeterService.getElmeterReportResponseResponseEntity(request));
    }
}
