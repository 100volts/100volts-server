package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.ElMeterAvrFifteenMinuteLoad;
import org.lci.volts.server.model.ElMeterDTO;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.GetAddListAndElMeterNamesDTO;
import org.lci.volts.server.model.responce.*;
import org.lci.volts.server.persistence.ElectricMeter;
import org.lci.volts.server.persistence.ElectricMeterData;
import org.lci.volts.server.repository.ElMeterRpository;
import org.lci.volts.server.repository.ElectricMeterDataRepository;
import org.lci.volts.server.repository.ElectricMeterRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.List;
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

    public GetAddListAndElMeterNamesResponse getAddressListWithNamesElectricMeterForCompany(final String companyId) {
        Set<ElectricMeter> allMetersFound =
                electricMeterRepository.findAllElMetersByCompanyName(companyId).orElseThrow();
        List<GetAddListAndElMeterNamesDTO> meterWithAddresses =
                allMetersFound.stream().map(meter ->
                                new GetAddListAndElMeterNamesDTO(meter.getName(), meter.getAddress()))
                        .toList();//collect(Collectors.toUnmodifiableList());
        return new GetAddListAndElMeterNamesResponse(meterWithAddresses);
    }

    public GetElMeterAndDataResponse getElectricMeterWithLastData(final int address, final String companyName) {
        final ElectricMeterData foundMeterWithData =
                electricMeterDataRepository.findAllElMetersWitDatalastRead(address, companyName).orElseThrow();
        final Set<ElectricMeterData> foundAvrMeterData =
                electricMeterDataRepository.findAvrElMetersData(address, companyName).orElseThrow();

        return new GetElMeterAndDataResponse(foundMeterWithData.getMeter().getName(), address,
                new ElMeterDataDTO(
                        BigDecimal.valueOf(foundMeterWithData.getMeter().getId()),
                        foundMeterWithData.getVoltageL1(), foundMeterWithData.getVoltageL1(),
                        foundMeterWithData.getVoltageL1(),
                        foundMeterWithData.getCurrentL1(), foundMeterWithData.getCurrentL2(),
                        foundMeterWithData.getCurrentL3(),
                        foundMeterWithData.getActivePowerL1(), foundMeterWithData.getActivePowerL2(),
                        foundMeterWithData.getActivePowerL3(), foundMeterWithData.getPowerFactorL1(),
                        foundMeterWithData.getPowerFactorL2(), foundMeterWithData.getPowerFactorL3(),
                        foundMeterWithData.getTotalActivePower(),
                        foundMeterWithData.getTotalActiveEnergyImportTariff1(),
                        foundMeterWithData.getTotalActiveEnergyImportTariff2()), getAvrData(foundAvrMeterData)
        );
    }

    private ElMeterAvrFifteenMinuteLoad getAvrData(Set<ElectricMeterData> data) {
        Set<BigDecimal> voltage = new HashSet<>();
        Set<BigDecimal> current = new HashSet<>();
        Set<BigDecimal> power = new HashSet<>();
        Set<BigDecimal> powerFactor =
                new HashSet<>();
        MathContext mc = new MathContext(5);

        data.forEach(electricMeterData -> {
            var vl1=electricMeterData.getVoltageL1();
            var vl1l2=vl1.add(electricMeterData.getVoltageL2());
            var vl1l2l3=vl1l2.add(electricMeterData.getVoltageL3());
            var vSumAvr=vl1l2l3.divide(BigDecimal.valueOf(3L),mc);

            voltage.add(vSumAvr);
            current.add(electricMeterData.getCurrentL1()
                    .add(electricMeterData.getCurrentL2().add(electricMeterData.getCurrentL3()))
                    .divide(BigDecimal.valueOf(3L),mc));
            power.add(electricMeterData.getActivePowerL1()
                    .add(electricMeterData.getActivePowerL2().add(electricMeterData.getActivePowerL3()))
                    .divide(BigDecimal.valueOf(3L),mc));
            powerFactor.add(electricMeterData.getPowerFactorL1()
                    .add(electricMeterData.getPowerFactorL2().add(electricMeterData.getPowerFactorL3()))
                    .divide(BigDecimal.valueOf(3L),mc));
        });
        BigDecimal volltsSum = BigDecimal.ZERO, currentSum = BigDecimal.ZERO, powerSum = BigDecimal.ZERO,
                powerFactorSum = BigDecimal.ZERO;
        for (BigDecimal v:voltage){
            volltsSum=volltsSum.add(v);
        }
        for(BigDecimal c:current){
            currentSum=currentSum.add(c);
        }
        for (BigDecimal pf:powerFactor){
            powerFactorSum=powerFactorSum.add(pf);
        }
        for (BigDecimal p:power){
            powerSum=powerSum.add(p);
        }
        return new ElMeterAvrFifteenMinuteLoad(
                volltsSum.divide(BigDecimal.valueOf(15),mc),
                currentSum.divide(BigDecimal.valueOf(15),mc),
                powerSum.divide(BigDecimal.valueOf(15),mc),
                powerFactorSum.divide(BigDecimal.valueOf(15),mc)
        );
    }

}
