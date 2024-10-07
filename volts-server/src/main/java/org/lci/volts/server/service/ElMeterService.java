package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.electricity.*;
import org.lci.volts.server.model.dto.settings.ElMeterSettings;
import org.lci.volts.server.model.record.ElMeterAvrFifteenMinuteLoad;
import org.lci.volts.server.model.request.electric.GetElMeterNameRequest;
import org.lci.volts.server.model.request.electric.data.GetElmeterReportRequest;
import org.lci.volts.server.model.request.electric.monthly.SetElMeterMonthlyRequest;
import org.lci.volts.server.model.request.electric.settings.ElMeterSettingsForCompanyRequest;
import org.lci.volts.server.model.request.electric.settings.ElectricMeterSettingRequest;
import org.lci.volts.server.model.responce.electric.*;
import org.lci.volts.server.model.responce.electric.data.ElMeterReadResponse;
import org.lci.volts.server.model.responce.electric.data.GetElMeterAndDataResponse;
import org.lci.volts.server.model.responce.electric.data.GetElMeterResponse;
import org.lci.volts.server.model.responce.electric.data.GetElectricMeterDailyTotPowerResponse;
import org.lci.volts.server.model.responce.electric.monthly.SetElMeterMonthlyResponse;
import org.lci.volts.server.model.responce.electric.settings.ElMeterSettingsForCompanyResponse;
import org.lci.volts.server.model.responce.electric.settings.ElectricMeterSettingResponse;
import org.lci.volts.server.persistence.Company;
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
    private final CompanyService companyService;

    public Set<ElectricMeter> findAllElectricMeters(final String companyName) {
        return electricMeterRepository.findAllElMetersByCompanyName(companyName).orElseThrow();
    }

    public Set<ElectricMeter> findAllElectricMeters(final String[] names, final String companyName) {
        Set<ElectricMeter> electricMeters = new HashSet<>();
        for (String name : names) {
            electricMeters.add(electricMeterRepository.findAllElMetersByCompanyNameAndNAme(name, companyName).orElseThrow());
        }
        return electricMeters;
    }

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
        Set<ElectricMeter> allMetersFound = electricMeterRepository.findAllElMetersByCompanyName(companyId).orElseThrow();
        int[] allMeterAddresses = allMetersFound.stream().mapToInt(ElectricMeter::getAddress).toArray();
        return new GetAddressListElMeterResponse(allMeterAddresses);
    }

    public GetAddListAndElMeterNamesResponse getAddressListWithNamesElectricMeterForCompany(final String companyId) {
        Set<ElectricMeter> allMetersFound = electricMeterRepository.findAllElMetersByCompanyName(companyId).orElseThrow();
        List<GetAddListAndElMeterNamesDTO> meterWithAddresses = allMetersFound.stream().map(meter -> new GetAddListAndElMeterNamesDTO(meter.getName(), meter.getAddress())).toList();//collect(Collectors.toUnmodifiableList());
        return new GetAddListAndElMeterNamesResponse(meterWithAddresses);
    }

    public GetElMeterAndDataResponse getElectricMeterWithLastData(final int address, final String companyName) {
        final ElectricMeterData foundMeterWithData = dataRepository.findAllElMetersWitDatalastRead(address, companyName).orElseThrow();
        final Set<ElectricMeterData> foundAvrMeterData = dataRepository.findAvrElMetersData(address, companyName).orElseThrow();
        var traf = getDailyTotPowerTariff(address, companyName);
        LocalDateTime startOfYesterday = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endOfYesterday = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
        List<DailyElMeterEnergyDTO> lastWeekEnergy = getSevenDayEnergy(address, companyName);

        ElectricMeterData yesterdays = dataRepository.getYesterdays(address, companyName, startOfYesterday, endOfYesterday).orElse(null);
        if (yesterdays != null) {
            return new GetElMeterAndDataResponse(
                    foundMeterWithData.getMeter().getName(),
                    address,
                    new ElMeterDataDTO(BigDecimal.valueOf(foundMeterWithData.getMeter().getId()),
                            foundMeterWithData.getVoltageL1(), foundMeterWithData.getVoltageL2(),
                            foundMeterWithData.getVoltageL3(), foundMeterWithData.getCurrentL1(),
                            foundMeterWithData.getCurrentL2(), foundMeterWithData.getCurrentL3(),
                            foundMeterWithData.getActivePowerL1(), foundMeterWithData.getActivePowerL2(),
                            foundMeterWithData.getActivePowerL3(), foundMeterWithData.getPowerFactorL1(),
                            foundMeterWithData.getPowerFactorL2(), foundMeterWithData.getPowerFactorL3(),
                            foundMeterWithData.getTotalActivePower(),
                            BigDecimal.valueOf(
                                    foundMeterWithData.getTotalActiveEnergyImportTariff1().longValue() - yesterdays.getTotalActiveEnergyImportTariff1().longValue()),
                            foundMeterWithData.getTotalActiveEnergyImportTariff2()),
                    getAvrData(foundAvrMeterData),
                    traf.dailyTariff(),
                    lastWeekEnergy);
        }
        return new GetElMeterAndDataResponse(
                foundMeterWithData.getMeter().getName(),
                address,
                new ElMeterDataDTO(BigDecimal.valueOf(foundMeterWithData.getMeter().getId()),
                        foundMeterWithData.getVoltageL1(), foundMeterWithData.getVoltageL2(),
                        foundMeterWithData.getVoltageL3(), foundMeterWithData.getCurrentL1(),
                        foundMeterWithData.getCurrentL2(), foundMeterWithData.getCurrentL3(),
                        foundMeterWithData.getActivePowerL1(), foundMeterWithData.getActivePowerL2(),
                        foundMeterWithData.getActivePowerL3(), foundMeterWithData.getPowerFactorL1(),
                        foundMeterWithData.getPowerFactorL2(), foundMeterWithData.getPowerFactorL3(),
                        foundMeterWithData.getTotalActivePower(),
                        BigDecimal.ZERO,
                        foundMeterWithData.getTotalActiveEnergyImportTariff2()),
                getAvrData(foundAvrMeterData),
                traf.dailyTariff(),
                lastWeekEnergy);
    }

    public List<DailyElMeterEnergyDTO> getSevenDayEnergy(final int address, final String companyName) {
        LocalDateTime startOfYesterday = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endOfYesterday = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
        ElectricMeterData foundMeterWithDataLast = dataRepository.findAllElMetersWitDatalastRead(address, companyName).orElseThrow();
        ElectricMeterData yesterdays = dataRepository.getYesterdays(address, companyName, startOfYesterday, endOfYesterday).orElse(null);
        List<DailyElMeterEnergyDTO> sevenDayEnergy = new ArrayList<>();

        if (yesterdays != null) {
            sevenDayEnergy.add(new DailyElMeterEnergyDTO(foundMeterWithDataLast.getDate().toString(), foundMeterWithDataLast.getDate().getDayOfWeek(), BigDecimal.valueOf(foundMeterWithDataLast.getTotalActiveEnergyImportTariff1().longValue() - yesterdays.getTotalActiveEnergyImportTariff1().longValue())));
        }else {
            startOfYesterday = startOfYesterday.minusDays(1);
            endOfYesterday = endOfYesterday.minusDays(1);
            foundMeterWithDataLast = dataRepository.findAllElMetersWitDatalastRead(address, companyName).orElseThrow();
            yesterdays = dataRepository.getYesterdays(address, companyName, startOfYesterday, endOfYesterday).orElse(null);
            sevenDayEnergy.add(new DailyElMeterEnergyDTO(foundMeterWithDataLast.getDate().toString(), foundMeterWithDataLast.getDate().getDayOfWeek(), BigDecimal.valueOf(foundMeterWithDataLast.getTotalActiveEnergyImportTariff1().longValue() - yesterdays.getTotalActiveEnergyImportTariff1().longValue())));
        }
        ElectricMeterData temp = yesterdays;

        ElectricMeterData tempy;

        for (int i = 0; i < 7; i++) {
            startOfYesterday = startOfYesterday.minusDays(1);
            endOfYesterday = endOfYesterday.minusDays(1);
            tempy = dataRepository.getYesterdays(address, companyName, startOfYesterday, endOfYesterday).orElse(null);
            if (tempy != null&&tempy.getDate()!=null) {
                sevenDayEnergy.add(new DailyElMeterEnergyDTO(temp.getDate().toString(),
                        temp.getDate().getDayOfWeek(), BigDecimal.valueOf(temp.getTotalActiveEnergyImportTariff1().longValue() - tempy.getTotalActiveEnergyImportTariff1().longValue())));
                temp = tempy;
            }
        }
        return sevenDayEnergy;
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
        Set<BigDecimal> powerFactor = new HashSet<>();
        MathContext mc = new MathContext(5);

        data.forEach(electricMeterData -> {
            var vl1 = electricMeterData.getVoltageL1();
            var vl1l2 = vl1.add(electricMeterData.getVoltageL2());
            var vl1l2l3 = vl1l2.add(electricMeterData.getVoltageL3());
            var vSumAvr = vl1l2l3.divide(BigDecimal.valueOf(3L), mc);

            voltage.add(vSumAvr);
            current.add(electricMeterData.getCurrentL1().add(electricMeterData.getCurrentL2().add(electricMeterData.getCurrentL3())).divide(BigDecimal.valueOf(3L), mc));
            power.add(electricMeterData.getActivePowerL1().add(electricMeterData.getActivePowerL2().add(electricMeterData.getActivePowerL3())).divide(BigDecimal.valueOf(3L), mc));
            powerFactor.add(electricMeterData.getPowerFactorL1().add(electricMeterData.getPowerFactorL2().add(electricMeterData.getPowerFactorL3())).divide(BigDecimal.valueOf(3L), mc));
        });
        BigDecimal volltsSum = BigDecimal.ZERO, currentSum = BigDecimal.ZERO, powerSum = BigDecimal.ZERO, powerFactorSum = BigDecimal.ZERO;
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
        return new ElMeterAvrFifteenMinuteLoad(volltsSum.divide(BigDecimal.valueOf(15), mc), currentSum.divide(BigDecimal.valueOf(15), mc), powerSum.divide(BigDecimal.valueOf(15), mc), powerFactorSum.divide(BigDecimal.valueOf(15), mc));
    }

    public GetElMeterReportResponse getElmeterReportResponseResponseEntity(final GetElmeterReportRequest request) {
        List<ElectricMeterData> foundMeterData = dataRepository.findAllElMetersWitDatalastReadLimit(request.address(), request.companyName(), request.pageLimit() * request.pages()).orElseThrow();
        List<List<ElMeterDataDTO>> allPages = new ArrayList<>();
        for (int i = 0; i < request.pages(); i++) {
            List<ElMeterDataDTO> page = new ArrayList<>();
            for (int j = 0; j < request.pageLimit(); j++) {
                page.add(foundMeterData.get((request.pageLimit() * i) + j).toDTO());
            }
            allPages.add(page);
        }
        return new GetElMeterReportResponse(allPages);
    }

    public SetElMeterMonthlyResponse setMonthlyReadData(final SetElMeterMonthlyRequest request) {
        ElectricMeterMonthlyData newMonthlyData = new ElectricMeterMonthlyData();
        newMonthlyData.setTz(request.tz());
        newMonthlyData.setTariff1(request.tariff1());
        newMonthlyData.setTarif2(request.tarif2());
        monthlyDataRepository.save(newMonthlyData);
        return new SetElMeterMonthlyResponse(true);
    }

    public GetElMeterNameResponse getElMeterNameForCompany(GetElMeterNameRequest request) {
        Set<ElectricMeter> foundElectrics = electricMeterRepository.findAllElMetersByCompanyName(request.companyName()).orElseThrow();
        List<String> response = new ArrayList<>();
        foundElectrics.forEach(electricMeter -> response.add(electricMeter.getName()));
        return new GetElMeterNameResponse(response);
    }

    public PutElectricMeterResponse createUpdateElectricMeter(PutElectricMeterRequest request) {
        try {
            if (request.meterNameNew() != null && !request.meterNameNew().isEmpty()) {
                updateMeterName(request);
            } else if (request.addressNew() != null) {
                updateMeterAddress(request);
            } else {
                createNewMeter(request);
            }
        } catch (Exception e) {
            return new PutElectricMeterResponse(false);
        }
        return new PutElectricMeterResponse(true);
    }

    private void updateMeterName(PutElectricMeterRequest request) {
        ElectricMeter foundMeter = electricMeterRepository.findAllElMetersByCompanyNameAndNAme(request.meterName(), request.companyName()).orElseThrow();
        foundMeter.setName(request.meterNameNew());
        electricMeterRepository.save(foundMeter);
    }

    private void updateMeterAddress(PutElectricMeterRequest request) {
        ElectricMeter foundMeter = electricMeterRepository.findAllElMetersByCompanyNameAndNAme(request.meterName(), request.companyName()).orElseThrow();
        foundMeter.setAddress(request.addressNew());
        electricMeterRepository.save(foundMeter);
    }

    private void createNewMeter(PutElectricMeterRequest request) {
        ElectricMeter newMeter = new ElectricMeter();
        Company company = companyService.getCompanyFromName(request.companyName());
        newMeter.setName(request.meterName());
        newMeter.setAddress(request.address());
        newMeter.setCompany(company);
        electricMeterRepository.save(newMeter);
    }

    public ElectricMeterSettingResponse getSettings(ElectricMeterSettingRequest request) {
        ElectricMeter foundMeter = electricMeterRepository.getReferenceById(request.id());
        return new ElectricMeterSettingResponse(foundMeter.getAddress(), foundMeter.getReadTimeGap());
    }

    public ElMeterSettingsForCompanyResponse getSettingsForAllMeters(final ElMeterSettingsForCompanyRequest request) {
        Set<ElectricMeter> allMetersForCompany = electricMeterRepository.findAllElMetersByCompanyName(request.companyName()).orElse(Set.of());
        List<ElMeterSettings> foundSettings = new ArrayList<>();
        allMetersForCompany.forEach(meter -> {
            ElectricMeterData data = dataRepository.findAllElMetersWitDatalastRead(meter.getAddress(), request.companyName()).orElse(null);
            if (data == null) {
                foundSettings.add(new ElMeterSettings(meter.getAddress(), meter.getName(), LocalDateTime.now().toString(), meter.getReadTimeGap()));
            } else {
                foundSettings.add(new ElMeterSettings(meter.getAddress(), meter.getName(), data.getDate().toString(), meter.getReadTimeGap()));
            }
        });
        return new ElMeterSettingsForCompanyResponse(foundSettings);
    }
}
