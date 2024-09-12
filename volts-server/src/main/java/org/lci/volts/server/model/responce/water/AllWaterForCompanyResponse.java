package org.lci.volts.server.model.responce.water;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lci.volts.server.model.dto.water.WaterDTO;

import java.math.BigDecimal;
import java.util.List;

public record AllWaterForCompanyResponse(List<WaterDTO> watter,@JsonProperty("sum_value") BigDecimal sumValues,@JsonProperty("meter_names") List<String> waterMeterNames) {
}
