package org.lci.volts.server.model.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataResponse {
    private String firstName;
    private String lastName;
    private String email;
}
