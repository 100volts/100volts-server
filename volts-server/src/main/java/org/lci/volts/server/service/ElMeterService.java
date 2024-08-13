package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.ElMeterDTO;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.responce.ElMeterReadResponse;
import org.lci.volts.server.model.responce.GetElMeterResponse;
import org.lci.volts.server.persistence.ElectricMeter;
import org.lci.volts.server.repository.ElMeterRpository;
import org.lci.volts.server.repository.ElectricMeterRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ElMeterService {
    private final ElMeterRpository repository;
    private final ElectricMeterRepository electricMeterRepository;

    public ElMeterReadResponse  setReadData(ElMeterDataDTO elMeterData) {
        return new ElMeterReadResponse(repository.saveElmeterData(elMeterData));
    }

    public boolean createElMeter(final ElMeterDTO request) {
        return repository.createElmeter(request);
    }

    public GetElMeterResponse getElectricMeter(final int address) {
        ElectricMeter foundMeter =electricMeterRepository.findByAddress(address).orElseThrow();
        return new GetElMeterResponse(foundMeter.getName(), foundMeter.getAddress());
    }
}
