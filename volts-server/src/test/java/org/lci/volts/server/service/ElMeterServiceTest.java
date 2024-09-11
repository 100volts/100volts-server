package org.lci.volts.server.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lci.volts.server.model.dto.electricity.ElMeterDTO;
import org.lci.volts.server.model.dto.electricity.ElMeterDataDTO;
import org.lci.volts.server.model.responce.electric.GetAddListAndElMeterNamesResponse;
import org.lci.volts.server.model.responce.electric.GetAddressListElMeterResponse;
import org.lci.volts.server.model.responce.electric.data.ElMeterReadResponse;
import org.lci.volts.server.model.responce.electric.data.GetElMeterAndDataResponse;
import org.lci.volts.server.model.responce.electric.data.GetElMeterResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.electric.ElectricMeterData;
import org.lci.volts.server.repository.electric.ElMeterRpository;
import org.lci.volts.server.repository.electric.ElectricMeterDataRepository;
import org.lci.volts.server.repository.electric.ElectricMeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ElMeterServiceTest {
    @MockBean
    private ElMeterRpository elMeterRpository;
    @MockBean
    private ElectricMeterRepository electricMeterRepository;
    @MockBean
    private ElectricMeterDataRepository electricMeterDataRepository;
    @Autowired
    private ElMeterService elMeterService;

    private static final String COMPANY_NAME = "Test_Company";

    @Test
    void setReadDataWillPositive() {
        //given
        ElMeterDataDTO request = new ElMeterDataDTO(
                BigDecimal.ONE,
                BigDecimal.valueOf(228.73),
                BigDecimal.valueOf(233.25),
                BigDecimal.valueOf(227.99),
                BigDecimal.valueOf(49.16),
                BigDecimal.valueOf(56.75),
                BigDecimal.valueOf(46.44),
                BigDecimal.valueOf(8307.05),
                BigDecimal.valueOf(11124.19),
                BigDecimal.valueOf(8626.97),
                BigDecimal.valueOf(0.738755),
                BigDecimal.valueOf(0.835376),
                BigDecimal.valueOf(0.810591),
                BigDecimal.valueOf(27010.097),
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
        //when
        when(elMeterRpository.saveElmeterData(request)).thenReturn(true);
        ElMeterReadResponse response = elMeterService.setReadData(request);
        //then
        assertNotNull(response);
        assertTrue(response.isSuccess());
    }

    @Test
    void setReadDataWillNegative() {
        //given
        ElMeterDataDTO request = new ElMeterDataDTO();
        //when then
        when(elMeterRpository.saveElmeterData(request)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> elMeterService.setReadData(request));
    }

    @Test
    void getElectricMeterPositive() {
        //given
        var mockCompany = new Company();
        mockCompany.setId(1L);
        mockCompany.setName(COMPANY_NAME);

        int address = 1;
        var mockMeter = new ElectricMeter();
        mockMeter.setId(1L);
        mockMeter.setName(COMPANY_NAME);
        mockMeter.setAddress(address);
        mockMeter.setCompany(mockCompany);
        given(electricMeterRepository.findByAddress(address)).willReturn(Optional.of(mockMeter));
        //when
        GetElMeterResponse foundMeter = elMeterService.getElectricMeter(address);
        //then
        assertNotNull(foundMeter);
        assertEquals(address,foundMeter.getAddress());
        assertEquals(mockMeter.getName(), foundMeter.getName());
    }

    @Test
    void getElectricMeternegativ() {
        //given
        int address = 1;

        given(electricMeterRepository.findByAddress(address)).willThrow(new RuntimeException());
        //when then
        assertThrows(RuntimeException.class, () -> elMeterService.getElectricMeter(address));
    }

    @Test
    void getAddressListElectricMeterForCompanyPositive() {
        //given
        final Company mockCompany = new Company();
        mockCompany.setId(1L);
        mockCompany.setName(COMPANY_NAME);

        int address = 1;
        ElectricMeter mockMeter = new ElectricMeter();
        mockMeter.setId(1L);
        mockMeter.setName(COMPANY_NAME);
        mockMeter.setAddress(address);
        mockMeter.setCompany(mockCompany);
        given(electricMeterRepository.findAllElMetersByCompanyName(COMPANY_NAME)).willReturn(
                Optional.of(Set.of(mockMeter)));
        //when
        GetAddressListElMeterResponse foundAddressList = elMeterService.getAddressListElectricMeterForCompany(
                COMPANY_NAME);
        //then
        assertNotNull(foundAddressList);
        assertEquals(foundAddressList.getAddressList()[0], address);
    }

    @Test
    void getAddressListElectricMeterForCompanyNegative() {
        //given
        given(electricMeterRepository.findAllElMetersByCompanyName(COMPANY_NAME)).willThrow(new RuntimeException());
        //when then
        assertThrows(RuntimeException.class, () -> elMeterService.getAddressListElectricMeterForCompany(COMPANY_NAME));
    }

    @Test
    void getAddressListWithNamesElectricMeterForCompanyPositive() {
        //given
        final Company mockCompany = new Company();
        mockCompany.setId(1L);
        mockCompany.setName(COMPANY_NAME);

        int address = 1;
        ElectricMeter mockMeter = new ElectricMeter();
        mockMeter.setId(1L);
        mockMeter.setName(COMPANY_NAME);
        mockMeter.setAddress(address);
        mockMeter.setCompany(mockCompany);
        given(electricMeterRepository.findAllElMetersByCompanyName(COMPANY_NAME)).willReturn(
                Optional.of(Set.of(mockMeter)));

        //when
        GetAddListAndElMeterNamesResponse foundAddresses =
                elMeterService.getAddressListWithNamesElectricMeterForCompany(
                        COMPANY_NAME);
        //then
        assertNotNull(foundAddresses);
        assertNotNull(foundAddresses.getAddressList());
        assertFalse(foundAddresses.getAddressList().isEmpty());
        assertNotNull(foundAddresses.getAddressList().get(0));
        assertEquals(address, foundAddresses.getAddressList().get(0).getAddress());
        assertEquals(COMPANY_NAME, foundAddresses.getAddressList().get(0).getName());
    }

    @Test
    void getAddressListWithNamesElectricMeterForCompanyNegative() {
        //given
        given(electricMeterRepository.findAllElMetersByCompanyName(COMPANY_NAME)).willThrow(new RuntimeException());

        //when then
        assertThrows(RuntimeException.class, () -> elMeterService.getAddressListWithNamesElectricMeterForCompany(
                COMPANY_NAME));
    }

    @Test
    @Disabled
    void getElectricMeterWithLastDataPositive() {
        //given
        final Company mockCompany = new Company();
        mockCompany.setId(1L);
        mockCompany.setName(COMPANY_NAME);

        int address = 1;
        ElectricMeter mockMeter = new ElectricMeter();
        mockMeter.setId(1L);
        mockMeter.setName(COMPANY_NAME);
        mockMeter.setAddress(address);
        mockMeter.setCompany(mockCompany);
        ElectricMeterData mockElectricMeterData = new ElectricMeterData();
        mockElectricMeterData.setId(1L);
        mockElectricMeterData.setMeter(mockMeter);
        mockElectricMeterData.setVoltageL1(BigDecimal.valueOf(228.73));
        mockElectricMeterData.setVoltageL2(BigDecimal.valueOf(232.23));
        mockElectricMeterData.setVoltageL3(BigDecimal.valueOf(254.98));
        mockElectricMeterData.setCurrentL1(BigDecimal.valueOf(39.16));
        mockElectricMeterData.setCurrentL2(BigDecimal.valueOf(43.12));
        mockElectricMeterData.setCurrentL3(BigDecimal.valueOf(54.43));
        mockElectricMeterData.setActivePowerL1(BigDecimal.valueOf(8307.05));
        mockElectricMeterData.setActivePowerL2(BigDecimal.valueOf(11124.19));
        mockElectricMeterData.setActivePowerL3(BigDecimal.valueOf(8626.97));
        mockElectricMeterData.setPowerFactorL1(BigDecimal.valueOf(0.738755));
        mockElectricMeterData.setPowerFactorL2(BigDecimal.valueOf(0.835376));
        mockElectricMeterData.setPowerFactorL3(BigDecimal.valueOf(0.810591));
        mockElectricMeterData.setTotalActiveEnergyImportTariff1(BigDecimal.valueOf(0.738755));
        mockElectricMeterData.setTotalActiveEnergyImportTariff2(BigDecimal.ZERO);
        mockElectricMeterData.setTotalActivePower(BigDecimal.valueOf(27010.097));
        mockElectricMeterData.setDate(LocalDateTime.now());

        ElectricMeterData mockAvr= new ElectricMeterData();

        given(electricMeterDataRepository.findAllElMetersWitDatalastRead(address, COMPANY_NAME)).willReturn(
                Optional.of(mockElectricMeterData));
        given(electricMeterDataRepository.findAvrElMetersData(address, COMPANY_NAME)).willReturn(Optional.of(Set.of(mockElectricMeterData)));
        given(electricMeterDataRepository.findDaielyRead(address,COMPANY_NAME)).willReturn(Optional.of(List.of(mockElectricMeterData)));
        //when
        GetElMeterAndDataResponse foundLastMeterData =
                elMeterService.getElectricMeterWithLastData(address, COMPANY_NAME);
        //then
        assertNotNull(foundLastMeterData);
        assertNotNull(foundLastMeterData.getName());
        assertEquals(COMPANY_NAME, foundLastMeterData.getName());
        assertEquals(address,foundLastMeterData.getAddress() );
        assertEquals(mockElectricMeterData.getActivePowerL1(),foundLastMeterData.getData().activepowerl1);
    }

    @Test
    void getElectricMeterWithLastDataNegative() {
        //given
        int address = 1;
        given(electricMeterDataRepository.findAllElMetersWitDatalastRead(address, COMPANY_NAME)).willThrow(new RuntimeException());
        //when then
        assertThrows(RuntimeException.class, () -> elMeterService.getElectricMeterWithLastData(address, COMPANY_NAME));
    }

    @Test
    void createElMeterPositive(){
        //given
        final int address = 1;
        final int companyAddress=1;
        ElMeterDTO mockElMeterDTO=new ElMeterDTO();
        mockElMeterDTO.setMeterAddress(address);
        mockElMeterDTO.setCompanyId(companyAddress);
        mockElMeterDTO.setMeterName(COMPANY_NAME);
        given(elMeterRpository.createElmeter(mockElMeterDTO)).willReturn(true);
        //when then
        assertTrue(elMeterService.createElMeter(mockElMeterDTO));
    }

    @Test
    void createElMeterNegative(){
        //given
        final int address = 1;
        final int companyAddress=1;
        ElMeterDTO mockElMeterDTO=new ElMeterDTO();
        mockElMeterDTO.setMeterAddress(address);
        mockElMeterDTO.setCompanyId(companyAddress);
        mockElMeterDTO.setMeterName(COMPANY_NAME);
        given(elMeterRpository.createElmeter(mockElMeterDTO)).willThrow(new RuntimeException());
        //when then
        assertThrows(RuntimeException.class,()->elMeterService.createElMeter(mockElMeterDTO));
    }
}