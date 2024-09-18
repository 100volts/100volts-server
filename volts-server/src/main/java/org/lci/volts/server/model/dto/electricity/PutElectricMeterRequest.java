package org.lci.volts.server.model.dto.electricity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PutElectricMeterRequest(@JsonProperty("company_name") String companyName,
                                      @JsonProperty("meter_name") String meterName,
                                      @JsonProperty("meter_name_new") String meterNameNew, Integer address,
                                      @JsonProperty("address_new") Integer addressNew) {
}
