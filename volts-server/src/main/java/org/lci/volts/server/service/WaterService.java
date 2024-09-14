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

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        List<Water> allWater = waterRepo.getAllWaterForCompany(companyName).orElseThrow();
        List<WaterData> allWaterData = waterDataRepo.getAllWaterDataForCompany(companyName).orElse(null);
        if (allWaterData == null) return null;
        List<WaterDTO> watter = allWater.stream().map(water -> {
            List<WaterDataDTO> data = waterDataToDataDTOList(allWaterData.stream().filter(waterData -> waterData.getWater().equals(water)).limit(2).toList());
            WaterDataDTO waterDataDTO = data.isEmpty() ? null : data.get(0);
            return new WaterDTO(water.getName(), water.getDescription(), water.getTs().toString(), getMetValue(allWaterData, water), waterDataDTO);
        }).toList();
        List<String> waterMeterNames=allWater.stream().map(Water::getName).toList();
        BigDecimal sumValues=BigDecimal.ZERO;
        for(WaterDTO waterDTO:watter) {
            sumValues=sumValues.add(waterDTO.value());
        }
        return new AllWaterForCompanyResponse(watter,sumValues,waterMeterNames);
    }

    private BigDecimal getMetValue(final List<WaterData> watterData, final Water waterMeter) {
        assert waterMeter != null;
        assert watterData != null;
        assert !watterData.isEmpty();
        List<BigDecimal> values = watterData.stream().filter(waterData -> waterData.getWater().equals(waterMeter)).limit(2).map(WaterData::getValue).toList();
        if (values.isEmpty()) return BigDecimal.ZERO;
        BigDecimal value = values.get(0);
        return values.size()<2?value:value.subtract(values.get(1)).round(new MathContext(2));
    }

    private List<WaterDataDTO> waterDataToDataDTOList(List<WaterData> data) {
        return data.stream().map(WaterData::toDTO).toList();
    }

    public Boolean createUpdateWaterRequest(CreateWaterRequest request) {
        if (request.nameNew() == null || request.nameNew().isEmpty()) {
            createWaterRequest(request);
        } else {
            updateWaterRequest(request);
        }
        return true;
    }

    public void createWaterRequest(CreateWaterRequest request) {
        Company company = companyRepo.findByName(request.companyName()).orElseThrow();
        Water water = new Water();
        water.setCompany(company);
        water.setName(request.name());
        water.setDescription(request.description());
        water.setTs(Timestamp.valueOf(LocalDateTime.now()));
        waterRepo.save(water);
    }

    public void updateWaterRequest(CreateWaterRequest request) {
        Company company = companyRepo.findByName(request.companyName()).orElseThrow();
        Water water = waterRepo.getWaterByNameAndCompanyName(request.companyName(), request.name()).orElseThrow();
        water.setCompany(company);
        water.setName(request.nameNew());
        water.setDescription(request.description());
        waterRepo.save(water);
    }

    public Boolean addWaterDateRequest(CreateWaterDataRequest request) {
        Water water = waterRepo.getWaterByNameAndCompanyName(request.companyName(), request.waterMeterName()).orElseThrow();
        WaterData waterData = new WaterData();
        waterData.setWater(water);
        waterData.setValue(request.value());
        waterData.setTs(Timestamp.valueOf(LocalDateTime.now()));
        waterDataRepo.save(waterData);
        return true;
    }
}
