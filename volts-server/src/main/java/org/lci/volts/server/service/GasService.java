package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.gas.GasDTO;
import org.lci.volts.server.model.dto.gas.GasDataDTO;
import org.lci.volts.server.model.dto.gas.GasFullDTO;
import org.lci.volts.server.model.request.gas.CreateGasDataRequest;
import org.lci.volts.server.model.request.gas.CreateGasRequest;
import org.lci.volts.server.model.request.gas.DeleteGasRequest;
import org.lci.volts.server.model.request.gas.GasReportRequest;
import org.lci.volts.server.model.responce.gas.AllGasForCompanyResponse;
import org.lci.volts.server.model.responce.gas.GasReportResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.gas.Gas;
import org.lci.volts.server.persistence.gas.GasData;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.GasDataRepository;
import org.lci.volts.server.repository.GasRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
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
        if (allGasData == null) return null;
        List<GasDTO> gas = allGas.stream().map(g -> {
            List<GasDataDTO> data = gasDataToDataDTOList(allGasData.stream().filter(waterData -> waterData.getGas().equals(g)).limit(2).toList());
            GasDataDTO waterDataDTO = data.isEmpty() ? null : data.get(0);
            return new GasDTO(g.getName(), g.getDescription(), g.getTs().toString(), getMetValue(allGasData, g), waterDataDTO);
        }).toList();
        List<String> waterMeterNames=allGas.stream().map(Gas::getName).toList();
        BigDecimal sumValues=BigDecimal.ZERO;
        for(GasDTO gasDTO:gas) {
            sumValues=sumValues.add(gasDTO.value());
        }
        return new AllGasForCompanyResponse(gas,sumValues,waterMeterNames);
        /*
        return new AllGasForCompanyResponse(allGas.stream().map(gas ->
                new GasDTO(gas.getName(), gas.getDescription(),gas.getTs().toString(),gasDataToDataDTOList( allGasData.stream().limit(2).filter(gasData -> gasData.getGas().equals(gas)).toList()))
        ).toList());
         */
    }

    private BigDecimal getMetValue(final List<GasData> watterData, final Gas waterMeter) {
        assert waterMeter != null;
        assert watterData != null;
        assert !watterData.isEmpty();
        List<BigDecimal> values = watterData.stream().filter(waterData -> waterData.getGas().equals(waterMeter)).limit(2).map(GasData::getValue).toList();
        if (values.isEmpty()) return BigDecimal.ZERO;
        BigDecimal value = values.get(0);
        return values.size()<2?value:value.subtract(values.get(1)).round(new MathContext(2));
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
        gasData.setTs(Timestamp.valueOf(request.date()));
        gasDataRepo.save(gasData);
        return true;
    }

    public List<GasFullDTO> getMonthlyData(final String company, final Date from, final Date to) {
        List<Gas> monthlyGas=gasRepo.getAllGasForCompanyForData(company,from,to).orElse(null);
        List<GasData> monthlyGasData=gasDataRepo.getAllGasForCompanyForData(company,from,to).orElse(null);
        List<GasFullDTO> gas = monthlyGas.stream().map(g -> {
            List<GasDataDTO> data = gasDataToDataDTOList(monthlyGasData.stream().filter(waterData -> waterData.getGas().equals(g)).limit(2).toList());
            return new GasFullDTO(g.getName(), g.getDescription(), g.getTs().toString(), getMetValue(monthlyGasData, g), data);
        }).toList();
        return gas;
    }

    public GasReportResponse getReport(GasReportRequest request) {
        List<GasData> foundGasReport=gasDataRepo.getGasReport(request.companyName(),request.name()).orElse(null);
        if(foundGasReport==null){return null;}
        return new GasReportResponse(foundGasReport.stream().map(GasData::toDTO).toList());
    }

    public boolean deleteData(final DeleteGasRequest request) {
        GasData foundDelete=gasDataRepo.getData(request.companyName(),request.name(),request.value()).orElse(null);
        if(foundDelete==null){return false;}
        gasDataRepo.delete(foundDelete);
        return true;
    }
}
