package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.config.SecurityConfig;
import org.lci.volts.server.model.request.auth.RegistrationRequest;
import org.lci.volts.server.model.request.company.RegistrationWithCompanyRequest;
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
    private final SecurityConfig securityConfig;

    private static final long BLANK_COMPANY_ID = 1L;

    @Transactional
    public void registerUserWhitCopany(final RegistrationWithCompanyRequest request) {
        validateEmailIsNotTaken(request.getEmail());

        CompanyUser toRegister = CompanyUser
                .builder()
                .email(request.getEmail())
                .password(securityConfig.passwordEncoder().encode(request.getPassword()))
                .firstName(request.getFirstName())
                .familyName(request.getLastName())
                .telephone(request.getTelephoneNumber())
                .company(companyRepository.findById(request.getCompanyId())
                        .orElseThrow(() -> new IllegalArgumentException("Company id is now found")))
                .role(Role.USER)
                .build();

        companyUserRepository.save(toRegister);

        //return authenticationService.authenticate(new AuthenticationRequest(request.getEmail(), request.getPassword()
//                        ,request.getMacAddress(),request.getIpAddress()
        // ));
    }

    public void registerUser(final RegistrationRequest request) {
        validateEmailIsNotTaken(request.getEmail());

        CompanyUser toRegister = CompanyUser
                .builder()
                .email(request.getEmail())
                .password(securityConfig.passwordEncoder().encode(request.getPassword()))
                .firstName(request.getFirstName())
                .familyName(request.getLastName())
                .telephone(request.getTelephoneNumber())
                .company(companyRepository.findById(BLANK_COMPANY_ID)
                        .orElseThrow(() -> new IllegalArgumentException("Company id is now found")))
                .role(Role.USER)
                .build();

        companyUserRepository.save(toRegister);
    }

    private void validateEmailIsNotTaken(final String email) {
        if (companyUserRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User name exists");
        }
    }
}
