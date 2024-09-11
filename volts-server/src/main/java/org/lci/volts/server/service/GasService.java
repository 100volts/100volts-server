package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.gas.GasDTO;
import org.lci.volts.server.model.dto.gas.GasDataDTO;
import org.lci.volts.server.model.request.gas.CreateGasDataRequest;
import org.lci.volts.server.model.request.gas.CreateGasRequest;
import org.lci.volts.server.model.responce.gas.AllGasForCompanyResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.gas.Gas;
import org.lci.volts.server.persistence.gas.GasData;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.GasDataRepository;
import org.lci.volts.server.repository.GasRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class GasService {
    private final GasRepository gasRepo;
    private final GasDataRepository gasDataRepo;
    private final CompanyRepository companyRepo;

    public AllGasForCompanyResponse getAllGasForCompany(String companyName) {
        List<Gas> allGas= gasRepo.getAllGasForCompany(companyName).orElseThrow();
        List<GasData>allGasData= gasDataRepo.getAllGasDataForCompany(companyName).orElse(null);
        return new AllGasForCompanyResponse(allGas.stream().map(gas ->
                new GasDTO(gas.getName(), gas.getDescription(),gas.getTs().toString(),gasDataToDataDTOList( allGasData.stream().limit(2).filter(gasData -> gasData.getGas().equals(gas)).toList()))
        ).toList());
    }

    private List<GasDataDTO> gasDataToDataDTOList(List<GasData> data){
        return data.stream().map(GasData::toDTO).toList();
    }

    public Boolean createUpdateGasRequest(CreateGasRequest request) {
        if(request.nameNew()==null||request.nameNew().isEmpty()){
            createGasRequest(request);
        } else {
            updateGasRequest(request);
        }
        return true;
    }

    public void createGasRequest(CreateGasRequest request) {
        Company company= companyRepo.findByName(request.companyName()).orElseThrow();
        Gas gas= new Gas();
        gas.setCompany(company);
        gas.setName(request.name());
        gas.setDescription(request.description());
        gas.setTs(Date.valueOf(LocalDate.now()));
        gasRepo.save(gas);
    }

    public void updateGasRequest(CreateGasRequest request) {
        Company company= companyRepo.findByName(request.companyName()).orElseThrow();
        Gas gas= gasRepo.getGasByNameAndCompanyName(request.companyName(),request.name()).orElseThrow();
        gas.setCompany(company);
        gas.setName(request.nameNew());
        gas.setDescription(request.description());
        gas.setTs(Date.valueOf(LocalDate.now()));
        gasRepo.save(gas);
    }

    public Boolean addGasDateRequest(CreateGasDataRequest request) {
        Gas gas= gasRepo.getGasByNameAndCompanyName(request.companyName(),request.gasMeterName()).orElseThrow();
        GasData gasData= new GasData();
        gasData.setGas(gas);
        gasData.setValue(request.value());
        gasData.setTs(Timestamp.valueOf(LocalDateTime.now()));
        gasDataRepo.save(gasData);
        return true;
    }
}
