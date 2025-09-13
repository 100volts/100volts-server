package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.controller.CollectControllerTimeSheetIfChangedRequest;
import org.lci.volts.server.model.request.controller.ControllerCreateUpdateRequest;
import org.lci.volts.server.model.responce.controller.CollectControllerTimeSheetIfChangedResponse;
import org.lci.volts.server.model.responce.controller.ControllerCreateUpdateResponse;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.controller.ControllerReadTimeSheet;
import org.lci.volts.server.persistence.controller.ControllerReadTimeSheetStatus;
import org.lci.volts.server.repository.CompanyRepository;
import org.lci.volts.server.repository.controller.ControllerReadTimeSheetRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ControllerReadTimeSheetService {
    private final ControllerReadTimeSheetRepository controllerReadTimeSheetRepository;
    private final CompanyRepository companyRepository;


    public CollectControllerTimeSheetIfChangedResponse getTimeSheetIfUpdated(CollectControllerTimeSheetIfChangedRequest request) {
        ControllerReadTimeSheet foundTimeSheet = controllerReadTimeSheetRepository
                .getControllerReadTimeSheetByCompanyName(request.companyName()).orElseThrow();
        return new CollectControllerTimeSheetIfChangedResponse(foundTimeSheet.getTimeSheet(), foundTimeSheet.getStatus().name());
    }

    public CollectControllerTimeSheetIfChangedResponse controllerHasSetTimeSheet(CollectControllerTimeSheetIfChangedRequest request) {
        ControllerReadTimeSheet foundTimeSheet = controllerReadTimeSheetRepository
                .getControllerReadTimeSheetByCompanyName(request.companyName()).orElseThrow();
        foundTimeSheet.setStatus(ControllerReadTimeSheetStatus.CONTROLLER_UP_TO_DATE);
        foundTimeSheet.setUpdatedAt(OffsetDateTime.now());
        foundTimeSheet= controllerReadTimeSheetRepository.save(foundTimeSheet);
        return new CollectControllerTimeSheetIfChangedResponse(foundTimeSheet.getTimeSheet(), foundTimeSheet.getStatus().name());
    }

    public ControllerCreateUpdateResponse createUpdate(ControllerCreateUpdateRequest request) {
        final Company controllerCompany=companyRepository.findByName(request.companyName()).orElse(null);
        if(controllerCompany==null){
            return new ControllerCreateUpdateResponse(false,request.timeSheet());
        }
        ControllerReadTimeSheet controllerReadTimeSheet = controllerReadTimeSheetRepository
                .getControllerReadTimeSheetByCompanyName(request.companyName())
                .orElse(null);

        if (controllerReadTimeSheet == null) {
            controllerReadTimeSheet = new ControllerReadTimeSheet();
            controllerReadTimeSheet.setCompany(controllerCompany);
            controllerReadTimeSheet.setCreatedAt(OffsetDateTime.now());
        }
        controllerReadTimeSheet.setTimeSheet(request.timeSheet());
        controllerReadTimeSheet.setUpdatedAt(OffsetDateTime.now());
        controllerReadTimeSheet.setStatus(ControllerReadTimeSheetStatus.CONTROLLER_NEEDS_UPDATE);
        controllerReadTimeSheet= controllerReadTimeSheetRepository.save(controllerReadTimeSheet);
        return new ControllerCreateUpdateResponse(true,controllerReadTimeSheet.getTimeSheet());
    }
}
