package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.jwt.JwtUtil;
import org.lci.volts.server.model.request.AuthenticationRequest;
import org.lci.volts.server.model.responce.AuthenticationResponse;
import org.lci.volts.server.repository.CompanyUserRepository;
import org.lci.volts.server.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@ReadingConverter
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final CompanyUserRepository companyUserRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(final JwtUtil jwtUtil, final CompanyUserRepository companyUserRepository, final AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.companyUserRepository = companyUserRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = companyUserRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "user not found"
                )
        );

        if(user.getAuthorities().contains(new SimpleGrantedAuthority(Role.BANNED.name()))){
            return AuthenticationResponse.builder()
                    .accessToken(null)
                    .role(user.getRole().toString())
                    .build();
        }

        var jwtToken = jwtUtil.generateToken(user);
        //TODO remove when created logger server
       // new LoginActivityLogger(jwtToken,request.getEmail(),request.getIpAddress(),request.getMacAddress()).start();
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .role(user.getRole().toString())
                .build();
    }
}