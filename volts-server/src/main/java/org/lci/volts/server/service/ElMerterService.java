package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.ElMeterDTO;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.responce.ElMeterReadResponse;
import org.lci.volts.server.repository.ElMeterRpository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ElMerterService {
    private final ElMeterRpository repository;

    public ElMeterReadResponse setReadData(ElMeterDataDTO elMeterData) {
        return new ElMeterReadResponse(repository.saveElmeterData(elMeterData));
    }

    public boolean createElMeter(final ElMeterDTO request) {
        return repository.createElmeter(request);
    }
}
