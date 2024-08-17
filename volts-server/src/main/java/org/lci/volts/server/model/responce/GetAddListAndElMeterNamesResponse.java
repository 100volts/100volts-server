package org.lci.volts.server.model.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.lci.volts.server.model.GetAddListAndElMeterNamesDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAddListAndElMeterNamesResponse {
    @JsonProperty("address_list")
    private List<GetAddListAndElMeterNamesDTO> addressList;
}