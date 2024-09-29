package org.lci.volts.server.model.responce.gas;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lci.volts.server.model.dto.gas.GasDTO;

import java.math.BigDecimal;
import java.util.List;

public record AllGasForCompanyResponse(List<GasDTO> gas, @JsonProperty("sum_value") BigDecimal sumValues,
                                       @JsonProperty("meter_names") List<String> waterMeterNames) {
}
