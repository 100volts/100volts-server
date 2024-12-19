package org.lci.volts.server.service;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.kpi.KPIDTO;
import org.lci.volts.server.model.request.kpi.KPICreateRequest;
import org.lci.volts.server.model.request.kpi.KPIDeleteRequest;
import org.lci.volts.server.model.request.kpi.KPIPayloadRequest;
import org.lci.volts.server.model.request.kpi.KPIUpdateRequest;
import org.lci.volts.server.model.responce.kpi.KPICreateResponse;
import org.lci.volts.server.model.responce.kpi.KPIPayloadResponse;
import org.lci.volts.server.model.responce.kpi.KPIUpdateByDateResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.Energy;
import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.electric.ElectricMeterData;
import org.lci.volts.server.persistence.kpi.Kpi;
import org.lci.volts.server.persistence.kpi.KpiData;
import org.lci.volts.server.persistence.kpi.KpiGroup;
import org.lci.volts.server.persistence.kpi.KpiSetting;
import org.lci.volts.server.persistence.production.Production;
import org.lci.volts.server.persistence.production.ProductionData;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.KPIEnergyRepository;
import org.lci.volts.server.repository.electric.ElectricMeterDataRepository;
import org.lci.volts.server.repository.electric.ElectricMeterRepository;
import org.lci.volts.server.repository.kpi.KPIDataRepository;
import org.lci.volts.server.repository.kpi.KPIGroupRepository;
import org.lci.volts.server.repository.kpi.KPIRepository;
import org.lci.volts.server.repository.kpi.KpiSettingsRepository;
import org.lci.volts.server.repository.production.ProductionDataRepository;
import org.lci.volts.server.repository.production.ProductionRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
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
    private final KPIGroupRepository kpiGroupRepository;
    private final KpiSettingsRepository kpiSettingsRepository;
    private final ProductionDataRepository productionDataRepository;
    private final ProductionRepository productionRepository;
    private final ElectricMeterDataRepository electricMeterDataRepository;
    private final ElectricMeterRepository electricMeterRepository;
    private final CompanyRepository companyRepository;

    public KPIPayloadResponse getAllFromCompany(KPIPayloadRequest request) {
        List<Kpi> kpiData= kpiRepository.getKPIPackage(request.company()).orElse(null);
        final OffsetDateTime dataTimeNow=OffsetDateTime.now();
        final OffsetDateTime startOfMonth=OffsetDateTime.of(LocalDateTime.of( dataTimeNow.getYear(), dataTimeNow.getMonth(),1,0,0),ZoneOffset.UTC);
        List<KpiData> kpiDataData=dataRepository. getKPIPDataBetweenTs(request.company(),startOfMonth,dataTimeNow).orElse(null);
        final List<KpiGroup> groups=kpiGroupRepository.findByCompanyName(request.company()).orElse(null);
        if(Objects.isNull(groups)){
            return null;
        }
        return new KPIPayloadResponse(kpiData.stream().map(kpi->kpi.toDTO(kpiDataData.stream().filter(kd->kd.getKpi().getName().equals(kpi.getName())).toList())).toList(),groups.stream().map(KpiGroup::toDto).toList());
    }

    public void recalculateKPIForProd(final String companyName,final Date date,@NotNull  final ProductionData production){
        return;
        //todo needs to be tested
        /*
        List<Kpi> allKpi= kpiRepository.getKPIPackage(companyName).orElse(null);
        if(Objects.isNull(allKpi)){
            return;
        }
        allKpi=allKpi.stream().filter(k->{ return k.getProductions().contains(production);}).toList();
        for(Kpi kpi:allKpi){
            updateKpiData(companyName,kpi.getName(),date);
        }
        */

    }

    public KPIUpdateByDateResponse updateKpiData(final String company, final String kpiName, final Date date){
        List<ProductionData> data =productionDataRepository.findAllByCompanyName(company).orElse(null);
        assert data != null;
        final Kpi kpi= kpiRepository.findByNameAndCompany(kpiName,company).orElse(null);
        if(Objects.isNull(kpi)){
            return null;
        }
        float buildSumElValueKpi=0;
        List<ElectricMeterData> elDate=new ArrayList<>();
        for(ElectricMeter electricMeter:kpi.getEnergy().getElectricMeters()){
            var elMeterDateFound=electricMeterDataRepository.findByCompanyAdrDate(electricMeter.getAddress(),company,date.toLocalDate().atStartOfDay(),date.toLocalDate().atTime(23,59)).orElse(null);
            if(Objects.nonNull(elMeterDateFound)&& !elMeterDateFound.isEmpty()){
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
        final Float kpiDataValue=buildSumElValueKpi/buildSumProdValueKpi;
        if(kpiDataValue.equals(Float.POSITIVE_INFINITY)){
            return null;
        }
        ZoneOffset zoneOffset = ZoneOffset.ofHours(2);
        OffsetDateTime timezoneDate=date.toLocalDate().atTime(12, 0).atOffset(zoneOffset);
        KpiData kpiData=new KpiData();
        kpiData.setKpi(kpi);
        kpiData.setProdData(buildProd);
        kpiData.setTs(timezoneDate);
        kpiData.setValue(buildSumElValueKpi/buildSumProdValueKpi);

        dataRepository.save(kpiData);

        return new KPIUpdateByDateResponse(kpiData.toDTO());
    }

    public KPIDTO createKPI(KPICreateRequest request) {
        final OffsetDateTime time=OffsetDateTime.now();
        //1. create new Energy
        List<ElectricMeter> electricMeters=new ArrayList<>();
        request.energy().electricEnergyName().forEach(meter->{
            electricMeters.add(electricMeterRepository.findAllElMetersByCompanyNameAndNAme(meter, request.company()).orElse(null));
        });
        Energy energy=new Energy();
        energy.setTs(time);
        energy.setEnergyIndex(Double.valueOf(request.energy().index()));
        energy.setElectricMeters(electricMeters);
        energy=kpiEnergyRepo.save(energy);
        //1.5 get company
        final Company company=companyRepository.findByName(request.company()).orElse(null);
        if(Objects.isNull(company)){
            return null;
        }
        //2. create new Group if dose not exists
        KpiGroup group=kpiGroupRepository.findByName(request.group().name()).orElse(null);
        if(group==null){
            group=new KpiGroup();
            group.setName(request.group().name());
            group.setDescriptor(request.group().description());
            group.setCompany(company);
            group=kpiGroupRepository.save(group);
        }
        //3. Get prod
        List<Production> prod=new ArrayList<>();
        request.prodNames().forEach(prodName->{
            prod.add(productionRepository.findAllProductionByCompanyName( prodName,request.company()).orElse(null));
        });
        //4. get settings
        final KpiSetting setting=kpiSettingsRepository.findByName(request.settings().name()).orElse(null);
        //5. create new kpi
        Kpi kpi=new Kpi();
        kpi.setName(request.KPIName());
        kpi.setDescriptor(request.description());
        kpi.setCompany(company);
        kpi.setTarget(Double.valueOf(request.target()));
        kpi.setGroupKpi(group);
        kpi.setProductions(prod);
        kpi.setSettings(setting);
        kpi.setEnergy(energy);
        kpi.setTs(time);
        var resultOfSave=kpiRepository.save(kpi);
        return resultOfSave.toDTO();
    }


    public KPIDTO updateKPI(final KPIUpdateRequest request) {
        final OffsetDateTime time=OffsetDateTime.now();
        //1. create new Energy

        //2. create new Group if dose not exists
        KpiGroup group=kpiGroupRepository.findByName(request.group().name()).orElse(null);
        if(group==null){
            group=new KpiGroup();
            group.setName(request.group().name());
            group=kpiGroupRepository.save(group);
        }
        //3. Get prod
        List<Production> prod=new ArrayList<>();
        request.prodNames().forEach(prodName->{
            prod.add(productionRepository.findAllProductionByCompanyName( prodName,request.company()).orElse(null));
        });
        //4. get settings
        final KpiSetting setting=kpiSettingsRepository.findByName(request.settings().name()).orElse(null);
        //5. create new kpi
        Kpi kpi=kpiRepository.findByNameAndCompany(request.KPIName(), request.company()).orElse(null);//new Kpi();

        List<ElectricMeter> electricMeters=new ArrayList<>();
        request.energy().electricEnergyName().forEach(meter->{
            electricMeters.add(electricMeterRepository.findAllElMetersByCompanyNameAndNAme(meter, request.company()).orElse(null));
        });
        Energy energy=kpiEnergyRepo.findById(kpi.getEnergy().getId().longValue()).orElse(new Energy());
        energy.setTs(time);
        energy.setEnergyIndex(Double.valueOf(request.energy().index()));
        energy.setElectricMeters(electricMeters);
        energy=kpiEnergyRepo.save(energy);

        kpi.setName(request.newName());
        kpi.setDescriptor(request.description());
        kpi.setCompany(companyRepository.findByName(request.company()).orElse(null));
        kpi.setTarget(Double.valueOf(request.target()));
        kpi.setGroupKpi(group);
        kpi.setProductions(prod);
        kpi.setSettings(setting);
        kpi.setEnergy(energy);
        kpi.setTs(time);
        var resultOfSave=kpiRepository.save(kpi);
        return resultOfSave.toDTO();
    }

    public KPIDTO delete(final KPIDeleteRequest request) {
        final Kpi kpiForDelete=kpiRepository.findByNameAndCompany(request.kpiName(),request.companyName()).orElseThrow();
        final List<KpiData> kpiDate=dataRepository.getKPIPByNameAndCompanyName(request.kpiName(),request.companyName()).orElse(null);
        if(Objects.nonNull(kpiForDelete)||!kpiDate.isEmpty()){
            dataRepository.deleteAll(kpiDate);
        }
        kpiRepository.delete(kpiForDelete);
        return kpiForDelete.toDTO();
    }
}
