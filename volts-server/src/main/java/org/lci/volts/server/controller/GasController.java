package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.gas.DailyGasMeterEnergyDTO;
import org.lci.volts.server.model.dto.gas.GasDTO;
import org.lci.volts.server.model.dto.gas.GasFullDTO;
import org.lci.volts.server.model.request.gas.*;
import org.lci.volts.server.model.request.water.WaterReportRequest;
import org.lci.volts.server.model.responce.gas.AllGasForCompanyResponse;
import org.lci.volts.server.model.responce.gas.DeleteGasResponse;
import org.lci.volts.server.model.responce.gas.GasReportResponse;
import org.lci.volts.server.model.responce.gas.GetSevenDayDataResponse;
import org.lci.volts.server.model.responce.water.WaterReportResponse;
import org.lci.volts.server.service.GasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/data/seven")
    public ResponseEntity<GetSevenDayDataResponse> getGasSevenData(@RequestBody GetSevenDayDataRequest request){
        return ResponseEntity.ok(new GetSevenDayDataResponse(gasService.getSevenDayEnergy(request.gasMeterName(),request.companyName())));
    }
    @PostMapping("/data/seven")
    public ResponseEntity<GetSevenDayDataResponse> getGasSixMonthData(@RequestBody GetSevenDayDataRequest request){
        return ResponseEntity.ok(new GetSevenDayDataResponse(gasService.getSixMonthData(request.gasMeterName(),request.companyName())));
    }
    @DeleteMapping("/data")
    public ResponseEntity<DeleteGasResponse> deleteGasData(@RequestBody DeleteGasRequest request){
        return ResponseEntity.ok(new DeleteGasResponse(gasService.deleteData(request)));
    }
    @PostMapping("/report")
    public ResponseEntity<GasReportResponse> gasReport(@RequestBody GasReportRequest request){
        return ResponseEntity.ok(gasService.getReport(request));
    }
    @PostMapping("/monthly")
    public ResponseEntity<List<GasFullDTO>> getMonthlyData(@RequestBody MonthlyGasRequest request){
        return ResponseEntity.ok(gasService.getMonthlyData(request.companyName(),request.from(),request.to()));
    }
}
