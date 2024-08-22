package org.lci.volts.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.responce.ElMeterReadResponse;
import org.lci.volts.server.model.responce.GetAddressListElMeterResponse;
import org.lci.volts.server.model.responce.GetElMeterResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.ElectricMeter;
import org.lci.volts.server.repository.ElMeterRpository;
import org.lci.volts.server.repository.ElectricMeterDataRepository;
import org.lci.volts.server.repository.ElectricMeterRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElMeterServiceTest {
    @Mock
    private ElMeterRpository elMeterRpository;
    @Mock
    private ElectricMeterRepository electricMeterRepository;
    @Mock
    private ElectricMeterDataRepository electricMeterDataRepository;
    private ElMeterService elMeterService;

    @BeforeEach
    void setUp(){
        elMeterService=new ElMeterService(elMeterRpository,electricMeterRepository,electricMeterDataRepository);
    }

    @Test
    void setReadDataWillPositive(){
        //given
        ElMeterDataDTO request = new ElMeterDataDTO(
                BigDecimal.ONE,
                 BigDecimal.valueOf(228.73),
                BigDecimal.valueOf(233.25),
                BigDecimal.valueOf(227.99),
                BigDecimal.valueOf(49.16),
                BigDecimal.valueOf( 56.75),
                BigDecimal.valueOf( 46.44),
                BigDecimal.valueOf( 8307.05),
                BigDecimal.valueOf( 11124.19),
                BigDecimal.valueOf( 8626.97),
                BigDecimal.valueOf( 0.738755),
                BigDecimal.valueOf( 0.835376),
                BigDecimal.valueOf( 0.810591),
                BigDecimal.valueOf( 27010.097),
                 BigDecimal.ZERO,
                 BigDecimal.ZERO
        );
        //when
        when(elMeterRpository.saveElmeterData(request)).thenReturn(true);
        ElMeterReadResponse response=elMeterService.setReadData(request);
        //then
        assertNotNull(response);
        assertTrue(response.isSuccess());
    }

    @Test
    void setReadDataWillNegative(){
        //given
        ElMeterDataDTO request = new ElMeterDataDTO();
        //when then
        when(elMeterRpository.saveElmeterData(request)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class,()-> elMeterService.setReadData(request));
    }

    @Test
    void getElectricMeterPositive(){
        //given
        var mockCompany=new Company();
        mockCompany.setId(1L);
        mockCompany.setName("Test_company");

        int address=1;
        var mockMeter=new ElectricMeter();
        mockMeter.setId(1L);
        mockMeter.setName("test");
        mockMeter.setAddress(address);
        mockMeter.setCompany(mockCompany);
        given(electricMeterRepository.findByAddress(address)).willReturn(Optional.of(mockMeter));
        //when
        GetElMeterResponse foundMeter=elMeterService.getElectricMeter(address);
        //then
        assertNotNull(foundMeter);
        assertEquals(foundMeter.getAddress(),address);
    }

    @Test
    void getElectricMeternegativ(){
        //given
        final Company mockCompany=new Company();
        mockCompany.setId(1L);
        mockCompany.setName("Test_company");

        int address=1;
        ElectricMeter mockMeter=new ElectricMeter();
        mockMeter.setId(1L);
        mockMeter.setName("test");
        mockMeter.setAddress(address);
        mockMeter.setCompany(mockCompany);
        given(electricMeterRepository.findByAddress(address)).willThrow(new RuntimeException());
        //when then
        assertThrows(RuntimeException.class,()->elMeterService.getElectricMeter(address));
    }

    @Test
    void getAddressListElectricMeterForCompanyPositive(){
        //given
        final String copmanyName="Test_Company";
        final Company mockCompany=new Company();
        mockCompany.setId(1L);
        mockCompany.setName("Test_company");

        int address=1;
        ElectricMeter mockMeter=new ElectricMeter();
        mockMeter.setId(1L);
        mockMeter.setName("test");
        mockMeter.setAddress(address);
        mockMeter.setCompany(mockCompany);
        given(electricMeterRepository.findAllElMetersByCompanyName(copmanyName)).willReturn(Optional.of(Set.of(mockMeter)));
        //when
        GetAddressListElMeterResponse foundAddressList=elMeterService.getAddressListElectricMeterForCompany(copmanyName);
        //then
        assertNotNull(foundAddressList);
        assertEquals(foundAddressList.getAddressList()[0],address);
    }
}