package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.kpi.KPIDTO;
import org.lci.volts.server.model.request.kpi.KPIPayloadRequest;
import org.lci.volts.server.model.responce.kpi.KPIPayloadResponse;
import org.lci.volts.server.persistence.kpi.Kpi;
import org.lci.volts.server.repository.KPIEnergyRepository;
import org.lci.volts.server.repository.kpi.KPIRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class KPIService {
    private final KPIEnergyRepository kpiEnergyRepo;
    private final KPIRepository kpiRepository;

    public KPIPayloadResponse getAllFromCompany(KPIPayloadRequest request) {
        KPIPayloadResponse response=new KPIPayloadResponse(null);
        List<KPIDTO> data=new ArrayList<>();
        List<Kpi> kpiData= kpiRepository.getKPIPackage(request.company()).orElse(null);
        return new KPIPayloadResponse(kpiData.stream().map(kpi->kpi.toDTO()).toList());
    }

    public void updateKpi(){
        
    }
}
