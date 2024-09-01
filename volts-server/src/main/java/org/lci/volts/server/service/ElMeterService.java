package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.ElMeterDTO;
import org.lci.volts.server.model.dto.ElMeterDataDTO;
import org.lci.volts.server.model.dto.GetAddListAndElMeterNamesDTO;
import org.lci.volts.server.model.dto.TotPowerDTO;
import org.lci.volts.server.model.record.ElMeterAvrFifteenMinuteLoad;
import org.lci.volts.server.model.request.electric.data.GetElmeterReportRequest;
import org.lci.volts.server.model.request.electric.monthly.SetElMeterMonthlyRequest;
import org.lci.volts.server.model.responce.electric.GetAddListAndElMeterNamesResponse;
import org.lci.volts.server.model.responce.electric.GetAddressListElMeterResponse;
import org.lci.volts.server.model.responce.electric.GetElmeterReportResponse;
import org.lci.volts.server.model.responce.electric.data.ElMeterReadResponse;
import org.lci.volts.server.model.responce.electric.data.GetElMeterAndDataResponse;
import org.lci.volts.server.model.responce.electric.data.GetElMeterResponse;
import org.lci.volts.server.model.responce.electric.data.GetElectricMeterDailyTotPowerResponse;
import org.lci.volts.server.model.responce.electric.monthly.SetElMeterMonthlyResponse;
import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.electric.ElectricMeterData;
import org.lci.volts.server.persistence.electric.ElectricMeterMonthlyData;
import org.lci.volts.server.repository.electric.ElMeterRpository;
import org.lci.volts.server.repository.electric.ElectricMeterDataRepository;
import org.lci.volts.server.repository.electric.ElectricMeterMonthlyDataRepository;
import org.lci.volts.server.repository.electric.ElectricMeterRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.*;
import java.util.ArrayList;
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
    private final ElectricMeterDataRepository dataRepository;
    private final ElectricMeterMonthlyDataRepository monthlyDataRepository;

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
                dataRepository.findAllElMetersWitDatalastRead(address, companyName).orElseThrow();
        final Set<ElectricMeterData> foundAvrMeterData =
                dataRepository.findAvrElMetersData(address, companyName).orElseThrow();
        var traf=getDailyTotPowerTariff(address,companyName);
        return new GetElMeterAndDataResponse(foundMeterWithData.getMeter().getName(), address,
                new ElMeterDataDTO(
                        BigDecimal.valueOf(foundMeterWithData.getMeter().getId()),
                        foundMeterWithData.getVoltageL1(), foundMeterWithData.getVoltageL2(),
                        foundMeterWithData.getVoltageL3(),
                        foundMeterWithData.getCurrentL1(), foundMeterWithData.getCurrentL2(),
                        foundMeterWithData.getCurrentL3(),
                        foundMeterWithData.getActivePowerL1(), foundMeterWithData.getActivePowerL2(),
                        foundMeterWithData.getActivePowerL3(), foundMeterWithData.getPowerFactorL1(),
                        foundMeterWithData.getPowerFactorL2(), foundMeterWithData.getPowerFactorL3(),
                        foundMeterWithData.getTotalActivePower(),
                        foundMeterWithData.getTotalActiveEnergyImportTariff1(),
                        foundMeterWithData.getTotalActiveEnergyImportTariff2()), getAvrData(foundAvrMeterData),traf.dailyTariff()
        );
    }

    public GetElectricMeterDailyTotPowerResponse getDailyTotPowerTariff(final int address, final String companyName) {
        List<ElectricMeterData> dailyTariff = dataRepository.findDaielyRead(address, companyName).orElseThrow();
        LocalDateTime dateTime = LocalDateTime.now();
        return new GetElectricMeterDailyTotPowerResponse(dailyTariff.stream().filter(dailyT -> dailyT.getDate().getDayOfMonth() == dateTime.getDayOfMonth()).map(dailyT -> new TotPowerDTO(dailyT.getTotalActiveEnergyImportTariff1(), dailyT.getDate().toString())).toList());
    }

    private ElMeterAvrFifteenMinuteLoad getAvrData(Set<ElectricMeterData> data) {
        Set<BigDecimal> voltage = new HashSet<>();
        Set<BigDecimal> current = new HashSet<>();
        Set<BigDecimal> power = new HashSet<>();
        Set<BigDecimal> powerFactor =
                new HashSet<>();
        MathContext mc = new MathContext(5);

        data.forEach(electricMeterData -> {
            var vl1 = electricMeterData.getVoltageL1();
            var vl1l2 = vl1.add(electricMeterData.getVoltageL2());
            var vl1l2l3 = vl1l2.add(electricMeterData.getVoltageL3());
            var vSumAvr = vl1l2l3.divide(BigDecimal.valueOf(3L), mc);

            voltage.add(vSumAvr);
            current.add(electricMeterData.getCurrentL1()
                    .add(electricMeterData.getCurrentL2().add(electricMeterData.getCurrentL3()))
                    .divide(BigDecimal.valueOf(3L), mc));
            power.add(electricMeterData.getActivePowerL1()
                    .add(electricMeterData.getActivePowerL2().add(electricMeterData.getActivePowerL3()))
                    .divide(BigDecimal.valueOf(3L), mc));
            powerFactor.add(electricMeterData.getPowerFactorL1()
                    .add(electricMeterData.getPowerFactorL2().add(electricMeterData.getPowerFactorL3()))
                    .divide(BigDecimal.valueOf(3L), mc));
        });
        BigDecimal volltsSum = BigDecimal.ZERO, currentSum = BigDecimal.ZERO, powerSum = BigDecimal.ZERO,
                powerFactorSum = BigDecimal.ZERO;
        for (BigDecimal v : voltage) {
            volltsSum = volltsSum.add(v);
        }
        for (BigDecimal c : current) {
            currentSum = currentSum.add(c);
        }
        for (BigDecimal pf : powerFactor) {
            powerFactorSum = powerFactorSum.add(pf);
        }
        for (BigDecimal p : power) {
            powerSum = powerSum.add(p);
        }
        return new ElMeterAvrFifteenMinuteLoad(
                volltsSum.divide(BigDecimal.valueOf(15), mc),
                currentSum.divide(BigDecimal.valueOf(15), mc),
                powerSum.divide(BigDecimal.valueOf(15), mc),
                powerFactorSum.divide(BigDecimal.valueOf(15), mc)
        );
    }

    public GetElmeterReportResponse getElmeterReportResponseResponseEntity(final GetElmeterReportRequest request) {
        List<ElectricMeterData> foundMeterData=
                dataRepository.findAllElMetersWitDatalastReadLimit(request.address(),request.companyName(),
                request.pageLimit()* request.pages()).orElseThrow();
        List<List<ElMeterDataDTO>> allPages=new ArrayList<>();
        for(int i=0;i< request.pages();i++){
            List<ElMeterDataDTO> page=new ArrayList<>();
            for (int j=0;j< request.pageLimit();j++){
                page.add(foundMeterData.get((request.pageLimit()*i)+j).toDTO());
            }
            allPages.add(page);
        }
        return new GetElmeterReportResponse(allPages);
    }

    public SetElMeterMonthlyResponse setMonthlyReadData(final SetElMeterMonthlyRequest request) {
        ElectricMeterMonthlyData newMonthlyData=new ElectricMeterMonthlyData();
        newMonthlyData.setTz(request.tz());
        newMonthlyData.setTariff1(request.tariff1());
        newMonthlyData.setTarif2(request.tarif2());
        monthlyDataRepository.save(newMonthlyData);
        return new SetElMeterMonthlyResponse(true);
    }
}
