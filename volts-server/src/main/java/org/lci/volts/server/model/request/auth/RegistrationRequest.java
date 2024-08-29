package org.lci.volts.server.model.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String ipAddress;
    private String macAddress;
}
