package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.error.ControllerErrorReportRequest;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.ConnectorReports;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.ConnectorReportsRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ConnectorReportsService {
    private final ConnectorReportsRepository errorRepo;
    private final CompanyRepository companyRepo;

    public Boolean writeReport(ControllerErrorReportRequest request) {
        Company company=companyRepo.findByName(request.companyName()).orElseThrow();
        ConnectorReports connectorReports=new ConnectorReports();
        connectorReports.setCompany(company);
        connectorReports.setTimestamp(Timestamp.valueOf(request.timeStamp()));
        connectorReports.setContent(request.content());
        errorRepo.save(connectorReports);
        return true;
    }
}
