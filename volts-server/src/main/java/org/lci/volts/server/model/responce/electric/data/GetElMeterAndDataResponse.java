package org.lci.volts.server.model.responce.electric.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.misc.Pair;
import org.lci.volts.server.model.dto.ElectricMeterEnergyDataDto;
import org.lci.volts.server.model.dto.electricity.DailyElMeterEnergyDTO;
import org.lci.volts.server.model.dto.electricity.EnergyMonthPairDTO;
import org.lci.volts.server.model.record.ElMeterAvrFifteenMinuteLoad;
import org.lci.volts.server.model.dto.electricity.ElMeterDataDTO;
import org.lci.volts.server.model.dto.electricity.TotPowerDTO;

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
    private List<DailyElMeterEnergyDTO> lastWeekEnergy;
    private List<EnergyMonthPairDTO> energyMonthPairDTOS;
    @JsonProperty("energy_data")
    private List<ElectricMeterEnergyDataDto> energyData;
}
