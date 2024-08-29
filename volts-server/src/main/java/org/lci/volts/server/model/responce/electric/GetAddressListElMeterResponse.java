package org.lci.volts.server.model.responce.electric;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAddressListElMeterResponse {
    @JsonProperty("address_list")
    private int[] addressList;
}
