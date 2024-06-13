package org.lci.volts.server.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationWithCompanyRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long companyId;
    private String telephoneNumber;
    private String ipAddress;
    private String macAddress;
}
