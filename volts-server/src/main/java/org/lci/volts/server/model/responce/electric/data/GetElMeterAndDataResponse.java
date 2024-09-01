package org.lci.volts.server.model.responce.electric.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.lci.volts.server.model.record.ElMeterAvrFifteenMinuteLoad;
import org.lci.volts.server.model.dto.ElMeterDataDTO;
import org.lci.volts.server.model.dto.TotPowerDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class GetElMeterAndDataResponse {
    private String name;
    private int address;
    @JsonProperty("electric_meter_data")
    private ElMeterDataDTO data;
    @JsonProperty("electric_meter_avr_data")
    private ElMeterAvrFifteenMinuteLoad avr;
    @JsonProperty("daily_tariff_data")
    private List<TotPowerDTO> dailyTariff;
}