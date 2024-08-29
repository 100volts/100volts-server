package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.responce.company.CompanyDataResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.CompanyUser;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.CompanyUserRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyUserRepository userRepository;

    public CompanyDataResponse getCompanyData(String companyName) {
        Company foundCompany=companyRepository.findByName(companyName).orElseThrow();
        return new CompanyDataResponse(foundCompany.getName());
    }

    public CompanyDataResponse getCompanyDataForUser(String userName) {
        CompanyUser companyUser=userRepository.findByEmail(userName).orElseThrow();
        Company foundCompany=companyUser.getCompany();
        return new CompanyDataResponse(foundCompany.getName());
    }
}
