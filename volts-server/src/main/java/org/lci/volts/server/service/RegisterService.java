package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.config.SecurityConfig;
import org.lci.volts.server.model.request.AuthenticationRequest;
import org.lci.volts.server.model.request.RegistrationRequest;
import org.lci.volts.server.model.responce.AuthenticationResponse;
import org.lci.volts.server.persistence.User;
import org.lci.volts.server.repository.UserRepository;
import org.lci.volts.server.type.Role;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final SecurityConfig securityConfig;
    public AuthenticationResponse registerUser(RegistrationRequest request) {

        var user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            //email already taken
            return null;
        }

        User toRegister = new User();
        toRegister.setEmail(request.getEmail());
        toRegister.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
        toRegister.setFirstName(request.getFirstName());
        toRegister.setFamilyName(request.getLastName());
        toRegister.setTelephone(request.getTelephoneNumber());
        toRegister.setId(toRegister.getIdCompany());
        toRegister.setRole(Role.USER);
        userRepository.save(toRegister);

        return authenticationService.authenticate(
                new AuthenticationRequest(request.getEmail(),request.getPassword()
//                        ,request.getMacAddress(),request.getIpAddress()
                ));
    }
}
