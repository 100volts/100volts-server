package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.repository.CompanyRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;


}
