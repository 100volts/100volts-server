package org.lci.volts.server.model.responce.electric;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.lci.volts.server.model.responce.electric.data.GetElMeterAndDataResponse;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAddressListElMeterResponse {
    @JsonProperty("address_list")
    List<GetElMeterAndDataResponse> allData;
}
