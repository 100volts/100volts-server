package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.production.CreteProductionRequest;
import org.lci.volts.server.model.request.production.GetProductionRequest;
import org.lci.volts.server.model.responce.production.CreteProductionResponse;
import org.lci.volts.server.model.responce.production.GetProductionResponse;
import org.lci.volts.server.persistence.Production;
import org.lci.volts.server.persistence.ProductionGroup;
import org.lci.volts.server.persistence.Units;
import org.lci.volts.server.repository.ProductionGroupRepository;
import org.lci.volts.server.repository.ProductionRepository;
import org.lci.volts.server.repository.UnitsRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ProductionService {
    private final ProductionRepository productionRepository;
    private final UnitsRepository unitsRepository;
    private final CompanyService companyService;
    private final ProductionGroupRepository groupRepository;

    public GetProductionResponse getProdByName(final GetProductionRequest request) {
        return new GetProductionResponse(
                productionRepository.findAllElMetersByCompanyName(request.name(), request.companyName()).orElseThrow().toDto());
    }

    public CreteProductionResponse createProdByName(CreteProductionRequest request) {
        Production newProduction = new Production();
        newProduction.setName(request.prodName());
        newProduction.setDescription(request.prodDescription());
        newProduction.setCompany(companyService.getCompanyFromName(request.companyName()));
        newProduction.setUnits(getUnitsFromName(request.unitsName()));
        newProduction.setGroups(List.of(getGroupFromName(request.groupName(),request.companyName())));

        productionRepository.save(newProduction);
        return new CreteProductionResponse(true);
    }

    public Units getUnitsFromName(String name){
        return unitsRepository.findByName(name).orElseThrow();
    }

    public ProductionGroup getGroupFromName(final String name,final String companyName){
        return groupRepository.findByName(name,companyName).orElseThrow();
    }
}
