package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.repository.KPIEnergyRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class KPIService {
    private final KPIEnergyRepository kpiEnergyRepo;

    public Object getAllFromCompany(Object request) {

        return null;
    }

    public void updateKpi(){
        
    }
}
