package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.kpi.KPIDTO;
import org.lci.volts.server.model.dto.kpi.KPIDataDTO;
import org.lci.volts.server.model.request.kpi.KPIPayloadRequest;
import org.lci.volts.server.model.responce.kpi.KPIPayloadResponse;
import org.lci.volts.server.model.responce.kpi.KPIUpdateByDateResponse;
import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.electric.ElectricMeterData;
import org.lci.volts.server.persistence.kpi.Kpi;
import org.lci.volts.server.persistence.kpi.KpiData;
import org.lci.volts.server.persistence.production.Production;
import org.lci.volts.server.persistence.production.ProductionData;
import org.lci.volts.server.repository.KPIEnergyRepository;
import org.lci.volts.server.repository.electric.ElectricMeterDataRepository;
import org.lci.volts.server.repository.kpi.KPIDataRepository;
import org.lci.volts.server.repository.kpi.KPIRepository;
import org.lci.volts.server.repository.production.ProductionDataRepository;
import org.lci.volts.server.repository.production.ProductionRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class KPIService {
    private final KPIEnergyRepository kpiEnergyRepo;
    private final KPIRepository kpiRepository;
    private final KPIDataRepository dataRepository;
    private final ProductionDataRepository productionDataRepository;
    private final ProductionRepository productionRepository;
    private final ElectricMeterDataRepository electricMeterDataRepository;

    public KPIPayloadResponse getAllFromCompany(KPIPayloadRequest request) {
        KPIPayloadResponse response=new KPIPayloadResponse(null);
        List<KPIDTO> data=new ArrayList<>();
        List<Kpi> kpiData= kpiRepository.getKPIPackage(request.company()).orElse(null);
        List<KpiData> kpiDataData=dataRepository.getKPIPDataLastMonth(request.company()).orElse(null);
        return new KPIPayloadResponse(kpiData.stream().map(kpi->kpi.toDTO(kpiDataData.stream().filter(kd->kd.getKpi().getName().equals(kpi.getName())).toList())).toList());
    }

    public KPIUpdateByDateResponse updateKpi(final String company, final String kpiName, final Date date){
        //1. Get all of the prods
        //2. get all of there data
        List<ProductionData> data =productionDataRepository.findAllByCompanyName(company).orElse(null);
        assert data != null;
        //3. for earch prod_data get and filter by dates
        // then write to data after all dates are finished get next date
        final Kpi kpi= kpiRepository.findByNameAndCompany(kpiName,company).orElse(null);
        if(Objects.isNull(kpi)){
            return null;
        }
        float buildSumElValueKpi=0;
        List<ElectricMeterData> elDate=new ArrayList<>();
        for(ElectricMeter electricMeter:kpi.getEnergy().getElectricMeters()){
            var elMeterDateFound=electricMeterDataRepository.findByCompanyAdrDate(electricMeter.getAddress(),company,date.toLocalDate().atStartOfDay(),date.toLocalDate().atTime(23,59)).orElse(null);
            if(Objects.nonNull(elMeterDateFound)){
                buildSumElValueKpi=buildSumElValueKpi+
                        (elMeterDateFound.get(0).getTotalActiveEnergyImportTariff1().longValue()
                                -elMeterDateFound.get(elMeterDateFound.size()-1).getTotalActiveEnergyImportTariff1().longValue());
                elDate.addAll(elMeterDateFound);
            }
        }

        final List<ProductionData> buildProd=new ArrayList<>();
        float buildSumProdValueKpi=0;
        for(int i=0;i<kpi.getProductions().size();i++){
            final int constI=i;
            final List<ProductionData> prodDataFiltered=data.stream()
                    .filter(d->d.getProduction().equals(kpi.getProductions().get(constI)))
                    .filter(d->d.getTs().equals(date))
                    .toList();
            float sumProdValueKpi=0;
            float sumElValueKpi=0;
            for(ProductionData prodData:prodDataFiltered){
                sumProdValueKpi=sumProdValueKpi+prodData.getValue().longValue();
            }


            buildProd.addAll(prodDataFiltered);
            buildSumProdValueKpi=buildSumProdValueKpi+sumProdValueKpi;
            buildSumElValueKpi=buildSumElValueKpi+sumElValueKpi;
        }

        ZoneOffset zoneOffset = ZoneOffset.ofHours(2);
        OffsetDateTime timezoneDate=date.toLocalDate().atTime(12, 0).atOffset(zoneOffset);
        KpiData kpiData=new KpiData();
        kpiData.setKpi(kpi);
        kpiData.setProdData(buildProd);
        kpiData.setTs(timezoneDate);
        kpiData.setValue(buildSumElValueKpi/buildSumProdValueKpi);

        //dataRepository.save(kpiData);

        return new KPIUpdateByDateResponse(kpiData.toDTO());
    }
}
