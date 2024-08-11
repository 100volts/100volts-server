package org.lci.volts.server.controller;


import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.responce.UserDataResponse;
import org.lci.volts.server.service.CompanyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/v1/company/user")
@RequiredArgsConstructor
public class CompanyUserController {
    private final CompanyUserService userService;

    @GetMapping
    public ResponseEntity<UserDataResponse> getUserData(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(userService.getUserData(authentication.getName()));
    }
}
