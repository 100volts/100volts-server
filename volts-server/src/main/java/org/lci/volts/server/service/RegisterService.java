package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.config.SecurityConfig;
import org.lci.volts.server.model.request.AuthenticationRequest;
import org.lci.volts.server.model.request.RegistrationRequest;
import org.lci.volts.server.model.responce.AuthenticationResponse;
import org.lci.volts.server.persistence.CompanyUser;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.CompanyUserRepository;
import org.lci.volts.server.type.Role;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class RegisterService {

    private final CompanyUserRepository companyUserRepository;
    private final CompanyRepository companyRepository;
    private final AuthenticationService authenticationService;
    private final SecurityConfig securityConfig;

    @Transactional
    public void registerUser(final RegistrationRequest request) {
        validateEmailIsNotTaken(request);

        CompanyUser toRegister = CompanyUser
                .builder()
                .email(request.getEmail())
                .password(securityConfig.passwordEncoder().encode(request.getPassword()))
                .firstName(request.getFirstName())
                .familyName(request.getLastName())
                .telephone(request.getTelephoneNumber())
                .company(companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new IllegalArgumentException("Company id is now found")))
                .role(Role.USER)
                .build();
        //toRegister.setEmail(request.getEmail());
        //toRegister.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
        //toRegister.setFirstName(request.getFirstName());
        //toRegister.setFamilyName(request.getLastName());
        //toRegister.setTelephone(request.getTelephoneNumber());
        //toRegister.setCompany(companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new IllegalArgumentException("Company id is now found")));
        //toRegister.setRole(Role.USER);
        companyUserRepository.save(toRegister);

        //return authenticationService.authenticate(new AuthenticationRequest(request.getEmail(), request.getPassword()
//                        ,request.getMacAddress(),request.getIpAddress()
       // ));
    }

    private void validateEmailIsNotTaken(final RegistrationRequest request) {
        if (companyUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User name exists");
        }
    }
}
