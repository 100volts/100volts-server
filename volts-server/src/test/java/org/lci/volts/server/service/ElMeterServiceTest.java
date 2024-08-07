package org.lci.volts.server.service;

import org.junit.jupiter.api.Test;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.lci.volts.server.model.responce.ElMeterReadResponse;
import org.lci.volts.server.repository.ElMeterRpository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class ElMeterServiceTest {
    @Mock
    private ElMeterRpository elMeterRpository;
    @Autowired
    private ElMeterService elMeterService;

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
        when(elMeterRpository.saveElmeterData(request)).thenReturn(false);
        ElMeterReadResponse response=elMeterService.setReadData(request);
        //then
        assertNotNull(response);
        assertFalse(response.isSuccess());
    }
}