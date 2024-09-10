package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.WatterDTO;
import org.lci.volts.server.model.dto.WatterDataDTO;
import org.lci.volts.server.model.request.watter.CreateWatterRequest;
import org.lci.volts.server.model.responce.watter.AllWatterForCompanyResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.watter.Watter;
import org.lci.volts.server.persistence.watter.WatterData;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.WatterDataRepository;
import org.lci.volts.server.repository.WatterRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class WatterService {
    private final WatterRepository watterRepo;
    private final WatterDataRepository watterDataRepo;
    private final CompanyRepository companyRepo;

    public AllWatterForCompanyResponse getAllWatterForCompany(String companyName) {
        List<Watter>allWatter= watterRepo.getAllWatterForCompany(companyName).orElseThrow();
        List<WatterData>allWatterData= watterDataRepo.getAllWatterDataForCompany(companyName).orElse(null);
        return new AllWatterForCompanyResponse(allWatter.stream().map(watter ->
            new WatterDTO(watter.getName(), watter.getDescription(),watter.getTs().toString(),watterDataToDataDTOList( allWatterData.stream().filter(watterData -> watterData.getWatter().equals(watter)).toList()))
        ).toList());
    }

    private List<WatterDataDTO> watterDataToDataDTOList(List<WatterData> data){
        return data.stream().map(WatterData::toDTO).toList();
    }

    public Boolean createWatterRequest(CreateWatterRequest request) {
        Company company= companyRepo.findByName(request.companyName()).orElseThrow();
        Watter watter= new Watter();
        watter.setCompany(company);
        watter.setName(request.name());
        watter.setDescription(request.description());
        watter.setTs(Date.valueOf(LocalDate.now()));
        watterRepo.save(watter);
        return true;
    }
}
