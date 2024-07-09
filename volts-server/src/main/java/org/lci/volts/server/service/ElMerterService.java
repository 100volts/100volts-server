package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.responce.ElMeterReadResponse;
import org.lci.volts.server.persistence.ElectricMeter;
import org.lci.volts.server.repository.ElMeterRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ElMerterService {
    private final ElMeterRepository elMeterRepository;

    public ElMeterReadResponse setReadData(ElMeterDataDTO elMeterData) {
        elMeterRepository.save(new ElectricMeter());
        return new ElMeterReadResponse(true);
    }
}
