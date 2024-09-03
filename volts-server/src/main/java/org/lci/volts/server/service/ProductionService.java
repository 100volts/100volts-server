package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.production.*;
import org.lci.volts.server.model.responce.production.*;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.production.Production;
import org.lci.volts.server.persistence.production.ProductionData;
import org.lci.volts.server.persistence.production.ProductionGroup;
import org.lci.volts.server.persistence.production.Units;
import org.lci.volts.server.repository.production.ProductionDataRepository;
import org.lci.volts.server.repository.production.ProductionGroupRepository;
import org.lci.volts.server.repository.production.ProductionRepository;
import org.lci.volts.server.repository.production.UnitsRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ProductionService {
    private final ProductionRepository productionRepository;
    private final ProductionDataRepository productionDataRepository;
    private final UnitsRepository unitsRepository;
    private final CompanyService companyService;
    private final ProductionGroupRepository groupRepository;

    private final ElMeterService elMeterService;

    public GetProductionResponse getProdByName(final GetProductionRequest request) {
        return new GetProductionResponse(
                productionRepository.findAllProductionByCompanyName(request.name(), request.companyName()).orElseThrow().toDto());
    }

    public CreteProductionResponse createProdByName(CreteProductionRequest request) {
        Production newProduction = new Production();
        newProduction.setName(request.prodName());
        newProduction.setDescription(request.prodDescription());
        Company foundCompany = companyService.getCompanyFromName(request.companyName());
        newProduction.setCompany(foundCompany);
        newProduction.setUnits(getUnitsFromName(request.unitsName()));
        Set<ElectricMeter>electric=elMeterService.findAllElectricMeters(request.elMeterNames(),request.companyName());
        newProduction.setElectricMeters(electric);
        newProduction.setGroups(List.of(cascadeGroupFromName(request.groupName(), request.companyName(), foundCompany)));
        newProduction.setTs(Date.valueOf(LocalDate.now()));

        productionRepository.save(newProduction);
        return new CreteProductionResponse(true);
    }

    public Units getUnitsFromName(String name) {
        return unitsRepository.findByName(name).orElseThrow();
    }

    public ProductionGroup getGroupFromName(final String name, final String companyName) {
        return groupRepository.findByName(name, companyName).orElse(null);
    }

    private ProductionGroup cascadeGroupFromName(final String name, final String companyName, final Company company) {
        ProductionGroup foundProdGroup = getGroupFromName(name, companyName);
        if (foundProdGroup == null) {

            ProductionGroup newPrdGroup=new ProductionGroup();
            newPrdGroup.setName(name);
            newPrdGroup.setCompany(company);
            newPrdGroup.setDescription("des");
            groupRepository.save(newPrdGroup);
            return newPrdGroup;
        }
        return foundProdGroup;
    }

    public AddProductionDataResponse addProductionData(AddProductionDataRequest request) {
        ProductionData newDate = new ProductionData();
        var prod = productionRepository.findAllProductionByCompanyName(request.productionName(), request.companyName()).orElseThrow();
        newDate.setProduction(prod.getId());
        newDate.setValue(request.value());
        productionDataRepository.save(newDate);
        return new AddProductionDataResponse(true);
    }

    public GetProductionAllResponse getProdAllByName(GetProductionAllRequest request) {
        return new GetProductionAllResponse(
                productionRepository.findAllProductionsAllCompanyName(request.companyName()).orElseThrow().stream().map(Production::toDto).toList());
    }

    public DeleteProductionResponse deleteProductionByName(final DeleteProductionRequest request) {
        Production foundProduction = productionRepository.findAllProductionByCompanyName(request.prodName(), request.companyName()).orElseThrow();
        //0 delete data
        //ProductionGroup foundPodGroup=groupRepository.findByName(request.prodName(), request.companyName());
        //1 delete group
        List<ProductionData> foundProdData = productionDataRepository.findAllProductionByCompanyName(foundProduction.getId()).orElseThrow();
        productionDataRepository.deleteAll(foundProdData);
        //2 delete production
        productionRepository.delete(foundProduction);
        return new DeleteProductionResponse(true);
    }
}
