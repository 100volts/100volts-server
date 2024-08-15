package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.ElMeterDTO;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.responce.ElMeterReadResponse;
import org.lci.volts.server.model.responce.GetAddressListElMeterResponse;
import org.lci.volts.server.model.responce.GetElMeterAndDataResponse;
import org.lci.volts.server.model.responce.GetElMeterResponse;
import org.lci.volts.server.persistence.ElectricMeter;
import org.lci.volts.server.persistence.ElectricMeterData;
import org.lci.volts.server.repository.ElMeterRpository;
import org.lci.volts.server.repository.ElectricMeterDataRepository;
import org.lci.volts.server.repository.ElectricMeterRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ElMeterService {
    private final ElMeterRpository repository;
    private final ElectricMeterRepository electricMeterRepository;
    private final ElectricMeterDataRepository electricMeterDataRepository;

    public ElMeterReadResponse setReadData(ElMeterDataDTO elMeterData) {
        return new ElMeterReadResponse(repository.saveElmeterData(elMeterData));
    }

    public boolean createElMeter(final ElMeterDTO request) {
        return repository.createElmeter(request);
    }

    public GetElMeterResponse getElectricMeter(final int address) {
        ElectricMeter foundMeter = electricMeterRepository.findByAddress(address).orElseThrow();
        return new GetElMeterResponse(foundMeter.getName(), foundMeter.getAddress());
    }

    public GetAddressListElMeterResponse getAddressListElectricMeterForCompany(final String companyId) {
        Set<ElectricMeter> allMetersFound =
                electricMeterRepository.findAllElMetersByCompanyName(companyId).orElseThrow();
        int[] allMeterAddresses = allMetersFound.stream().mapToInt(ElectricMeter::getAddress).toArray();
        return new GetAddressListElMeterResponse(allMeterAddresses);
    }

    public GetElMeterAndDataResponse getElectricMeterWithLastData(final int address,final String companyName) {
        final ElectricMeterData foundMeterWithData =
                electricMeterDataRepository.findAllElMetersWitDatalastRead(address,companyName).orElseThrow();
        return new GetElMeterAndDataResponse(foundMeterWithData.getMeter().getName(), address,
                new ElMeterDataDTO(
                        BigDecimal.valueOf(foundMeterWithData.getMeter().getId()),
                        foundMeterWithData.getVoltageL1(), foundMeterWithData.getVoltageL1(), foundMeterWithData.getVoltageL1(),
                        foundMeterWithData.getCurrentL1(), foundMeterWithData.getCurrentL2(), foundMeterWithData.getCurrentL3(),
                        foundMeterWithData.getActivePowerL1(), foundMeterWithData.getActivePowerL2(),
                        foundMeterWithData.getActivePowerL3(), foundMeterWithData.getPowerFactorL1(),
                        foundMeterWithData.getPowerFactorL2(), foundMeterWithData.getPowerFactorL3(),
                        foundMeterWithData.getTotalActivePower(),
                        foundMeterWithData.getTotalActiveEnergyImportTariff1(),
                        foundMeterWithData.getTotalActiveEnergyImportTariff2())
        );
    }
}
