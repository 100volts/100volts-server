package org.lci.volts.server.model.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("company_data")
public class CompanyDataResponse {
    @JsonProperty("company_name")
    public String name;
}
