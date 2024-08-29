package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.company.CompanyDataRequest;
import org.lci.volts.server.model.responce.company.CompanyDataResponse;
import org.lci.volts.server.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<CompanyDataResponse> getCompanyData(@RequestBody CompanyDataRequest companyDataRequest) {
        return ResponseEntity.ok(companyService.getCompanyData(companyDataRequest.getCompanyName()));
    }
    @GetMapping("/by/user")
    public ResponseEntity<CompanyDataResponse> getCompanyDataForUser( @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return ResponseEntity.ok(companyService.getCompanyDataForUser(authentication.getName()));
    }
}