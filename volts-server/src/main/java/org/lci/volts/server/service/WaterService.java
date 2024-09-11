package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.water.WaterDTO;
import org.lci.volts.server.model.dto.water.WaterDataDTO;
import org.lci.volts.server.model.request.water.CreateWaterDataRequest;
import org.lci.volts.server.model.request.water.CreateWaterRequest;
import org.lci.volts.server.model.responce.water.AllWaterForCompanyResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.water.Water;
import org.lci.volts.server.persistence.water.WaterData;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.WaterDataRepository;
import org.lci.volts.server.repository.WaterRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class WaterService {
    private final WaterRepository waterRepo;
    private final WaterDataRepository waterDataRepo;
    private final CompanyRepository companyRepo;

    public AllWaterForCompanyResponse getAllWaterForCompany(String companyName) {
        List<Water>allWater= waterRepo.getAllWaterForCompany(companyName).orElseThrow();
        List<WaterData>allWaterData= waterDataRepo.getAllWaterDataForCompany(companyName).orElse(null);
        return new AllWaterForCompanyResponse(allWater.stream().map(water ->
            new WaterDTO(water.getName(), water.getDescription(),water.getTs().toString(),waterDataToDataDTOList( allWaterData.stream().filter(waterData -> waterData.getWater().equals(water)).toList()))
        ).toList());
    }

    private List<WaterDataDTO> waterDataToDataDTOList(List<WaterData> data){
        return data.stream().map(WaterData::toDTO).toList();
    }

    public Boolean createUpdateWaterRequest(CreateWaterRequest request) {
        if(request.nameNew()==null||request.nameNew().isEmpty()){
            createWaterRequest(request);
        } else {
            updateWaterRequest(request);
        }
        return true;
    }

    public void createWaterRequest(CreateWaterRequest request) {
        Company company= companyRepo.findByName(request.companyName()).orElseThrow();
        Water water= new Water();
        water.setCompany(company);
        water.setName(request.name());
        water.setDescription(request.description());
        water.setTs(Date.valueOf(LocalDate.now()));
        waterRepo.save(water);
    }

    public void updateWaterRequest(CreateWaterRequest request) {
        Company company= companyRepo.findByName(request.companyName()).orElseThrow();
        Water water= waterRepo.getWaterByNameAndCompanyName(request.companyName(),request.name()).orElseThrow();
        water.setCompany(company);
        water.setName(request.nameNew());
        water.setDescription(request.description());
        water.setTs(Date.valueOf(LocalDate.now()));
        waterRepo.save(water);
    }

    public Boolean addWaterDateRequest(CreateWaterDataRequest request) {
        Water water= waterRepo.getWaterByNameAndCompanyName(request.companyName(),request.waterMeterName()).orElseThrow();
        WaterData waterData= new WaterData();
        waterData.setWater(water);
        waterData.setValue(request.value());
        waterData.setTs(Date.valueOf(LocalDate.now()));
        waterDataRepo.save(waterData);
        return true;
    }
}
