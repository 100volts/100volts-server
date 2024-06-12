package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.AuthenticationRequest;
import org.lci.volts.server.model.request.RegistrationRequest;
import org.lci.volts.server.model.responce.AuthenticationResponse;
import org.lci.volts.server.service.AuthenticationService;
import org.lci.volts.server.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RegisterService registerService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            @RequestHeader("Host") String remoteAddress
    ) {
        //request.setIpAddress(remoteAddress);
        var response = authenticationService.authenticate(request);
        return response.getAccessToken() != null ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody RegistrationRequest request,
            @RequestHeader("Host") String remoteAddress
    ) {
        request.setIpAddress(remoteAddress);
        registerService.registerUser(request);
        return ResponseEntity.ok("ok");
    }

}