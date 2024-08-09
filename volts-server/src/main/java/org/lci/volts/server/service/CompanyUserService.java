package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.responce.UserDataResponse;
import org.lci.volts.server.persistence.CompanyUser;
import org.lci.volts.server.repository.CompanyUserRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class CompanyUserService {
    private final CompanyUserRepository userRepository;

    public UserDataResponse getUserData(final String email) {
        CompanyUser userData = userRepository.findByEmail(email).orElseThrow();
        return new UserDataResponse(userData.getFirstName(), userData.getFamilyName(), userData.getEmail());
    }
}
