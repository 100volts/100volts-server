package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.electricity.ElMeterWithDataDTO;
import org.lci.volts.server.model.dto.electricity.MonthValueDTO;
import org.lci.volts.server.model.dto.production.ProductionDTO;
import org.lci.volts.server.model.dto.production.ProductionDataDTO;
import org.lci.volts.server.model.dto.production.ProductionPackageDTO;
import org.lci.volts.server.model.record.ElDataStartEnd;
import org.lci.volts.server.model.record.ProdWhitStartEndData;
import org.lci.volts.server.model.request.production.*;
import org.lci.volts.server.model.responce.production.*;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.electric.ElectricMeterData;
import org.lci.volts.server.persistence.production.Production;
import org.lci.volts.server.persistence.production.ProductionData;
import org.lci.volts.server.persistence.production.ProductionGroup;
import org.lci.volts.server.persistence.production.Units;
import org.lci.volts.server.repository.electric.ElectricMeterDataRepository;
import org.lci.volts.server.repository.electric.ElectricMeterRepository;
import org.lci.volts.server.repository.production.ProductionDataRepository;
import org.lci.volts.server.repository.production.ProductionGroupRepository;
import org.lci.volts.server.repository.production.ProductionRepository;
import org.lci.volts.server.repository.production.UnitsRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

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
    private final ElectricMeterRepository electricMeterRepository;
    private final KPIService kpiService;

    private final ElMeterService elMeterService;

    public GetProductionResponse getProdByName(final GetProductionRequest request) {
        Production foundProduction =
                productionRepository.findAllProductionByCompanyName(request.name(), request.companyName())
                        .orElseThrow();

        List<ProductionData> last6Months =
                productionDataRepository.getLast6Months(foundProduction.getId(), LocalDate.now()).orElseThrow();
        Map<Month, List<ProductionData>> groupedByMonth =
                last6Months.stream().collect(Collectors.groupingBy(pd -> pd.getTs().toLocalDate().getMonth()));
        Set<Month> prodMonts = groupedByMonth.keySet();
        List<MonthValueDTO> groupedByMonthDTO = new ArrayList<>();
        for (Month month : prodMonts) {
            BigDecimal sumValue = BigDecimal.ZERO;
            List<ProductionData> productionDataList = groupedByMonth.get(month);
            for (ProductionData productionData : productionDataList) {
                sumValue = sumValue.add(productionData.getValue());
            }
            groupedByMonthDTO.add(new MonthValueDTO(month, sumValue));
        }
        groupedByMonthDTO.sort(Comparator.comparing(MonthValueDTO::month));
        return new GetProductionResponse(
                productionRepository.findAllProductionByCompanyName(request.name(), request.companyName()).orElseThrow()
                        .toDto(), groupedByMonthDTO);
    }

    public CreteProductionResponse createProdByName(CreteProductionRequest request) {
        Production newProduction = new Production();
        newProduction.setName(request.prodName());
        newProduction.setDescription(request.prodDescription());
        Company foundCompany = companyService.getCompanyFromName(request.companyName());
        newProduction.setCompany(foundCompany);
        newProduction.setUnits(getUnitsFromName(request.unitsName()));
        Set<ElectricMeter> electric =
                elMeterService.findAllElectricMeters(request.elMeterNames(), request.companyName());
        newProduction.setElectricMeters(electric);
        newProduction.setGroups(
                List.of(cascadeGroupFromName(request.groupName(), request.companyName(), foundCompany)));
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

            ProductionGroup newPrdGroup = new ProductionGroup();
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
        var prod = productionRepository.findAllProductionByCompanyName(request.productionName(), request.companyName())
                .orElseThrow();
        newDate.setProduction(prod);
        newDate.setValue(request.value());
        newDate.setTs(request.date());
        productionDataRepository.save(newDate);
        kpiService.recalculateKPIForProd(request.companyName(),request.date(),newDate);
        return new AddProductionDataResponse(true);
    }

    public GetProductionAllResponse getProdAllByName(final GetProductionAllRequest request) {
        final String companyName = request.companyName();

        List<Production> foundProd = productionRepository.findAllProductionsAllCompanyName(companyName).orElseThrow();
        final List<ProdWhitStartEndData> allElectricMeterDataForProductions = getAllMeterForACompanyAndTherirLastsReadData(companyName, foundProd);
        List<ProductionPackageDTO> productionPackageDTOS = new ArrayList<>();
        List<ProductionData> last6Months =
                productionDataRepository.getLast6MonthsForCompany(companyName, LocalDate.now()).orElseThrow();
        foundProd.forEach(production -> {
            Map<Month, List<ProductionData>> groupedByMonth =
                    last6Months.stream().filter(data -> data.getProduction().getName().equals(production.getName()))
                            .collect(Collectors.groupingBy(pd -> pd.getTs().toLocalDate().getMonth()));
            Set<Month> prodMonts = groupedByMonth.keySet();
            List<MonthValueDTO> groupedByMonthDTO = new ArrayList<>();
            for (Month month : prodMonts) {
                BigDecimal sumValue = BigDecimal.ZERO;
                List<ProductionData> productionDataList = groupedByMonth.get(month);
                for (ProductionData productionData : productionDataList) {
                    sumValue = sumValue.add(productionData.getValue().setScale(0, RoundingMode.HALF_UP));
                }
                groupedByMonthDTO.add(new MonthValueDTO(month, sumValue));
            }
            //creation of el-meter data for prod
            List<ElMeterWithDataDTO> elData = sortOutProductionElectricMeterData(production, groupedByMonthDTO, allElectricMeterDataForProductions);

            //get last 10 from db
            List<ProductionData> foundData = last6Months
                    .stream()
                    .filter(data -> data.getProduction().getName().equals(production.getName()))
                    .sorted(Comparator.comparing(ProductionData::getTs).reversed())
                    .limit(10).toList();

            productionPackageDTOS.add(
                    production.toPackageDTO(groupedByMonthDTO, foundData.stream().map(ProductionData::toDTO).toList(),
                            elData));

        });
        return new GetProductionAllResponse(productionPackageDTOS);
    }

    private static List<ElMeterWithDataDTO> sortOutProductionElectricMeterData(Production production, List<MonthValueDTO> groupedByMonthDTO, List<ProdWhitStartEndData> allElectricMeterDataForProductions) {
        List<ElMeterWithDataDTO> elData=null;
        groupedByMonthDTO.sort(Comparator.comparing(MonthValueDTO::month));
        final List<ProdWhitStartEndData> filteredMeterProdData= allElectricMeterDataForProductions.stream()
                .filter(prodEl->prodEl.prod().equals(production)).toList();
        if(filteredMeterProdData.size()>1){
            elData=null;
        }else if(filteredMeterProdData.size()==0){
            elData=null;
        }else {
            elData=List.of(
                    new ElMeterWithDataDTO(
                            filteredMeterProdData.get(0).startEndData().get(0).meter().getAddress(),
                            filteredMeterProdData.get(0).startEndData().get(0).meter().getName(),
                            String.valueOf(filteredMeterProdData.get(0).startEndData().get(0).end().getTotalActiveEnergyImportTariff1().subtract(filteredMeterProdData.get(0).startEndData().get(0).start().getTotalActiveEnergyImportTariff1())),
                            //String.valueOf(filteredMeterProdData.get(0).startEndData.get(0).end.getTotalActivePower().subtract(filteredMeterProdData.get(0).startEndData.get(0).start.getTotalActivePower())),
                            "0",
                            filteredMeterProdData.get(0).startEndData().get(0).end().getDate().toString(),
                            filteredMeterProdData.get(0).startEndData().get(0).end().getDate().getMonth().name()));
        }
        return elData;
    }


    public List<ProdWhitStartEndData> getAllMeterForACompanyAndTherirLastsReadData(final String companyName, final List<Production> prods) {
        ArrayList<ElectricMeter> electricMeters = new ArrayList<>();
        prods.forEach(prod -> {
            prod.getElectricMeters().forEach(electricMeter -> {
                if (!electricMeters.contains(electricMeter)) {
                    electricMeters.add(electricMeter);
                }
            });
        });

        final LocalDateTime endOfMothLimit = LocalDate.now().minusMonths(1).minusDays(1).atTime(LocalTime.MAX);

        List<ElDataStartEnd> foundDataStartEnd =elMeterService.getMonthlyData(electricMeters,companyName,endOfMothLimit,0);
        //use dis to get data back one moth ago

        return mapElectricDataToProd(foundDataStartEnd,prods);
    }

    private List<ProdWhitStartEndData> mapElectricDataToProd(final List<ElDataStartEnd> foundDataStartEnd, final List<Production> prods) {
        List<ProdWhitStartEndData> sortedMeterData = new ArrayList<>();

        prods.forEach(prod -> {
            List<ElDataStartEnd> dataStartEnd=new ArrayList<>();
            prod.getElectricMeters().forEach(electricMeter -> {
                dataStartEnd.add(foundDataStartEnd.stream().filter(fData-> fData.meter().equals(electricMeter)).findFirst().orElse(null));
            });
            sortedMeterData.add(new ProdWhitStartEndData(prod,dataStartEnd));
        });

        return sortedMeterData;
    }

    public DeleteProductionResponse deleteProductionByName(final DeleteProductionRequest request) {
        Production foundProduction =
                productionRepository.findAllProductionByCompanyName(request.prodName(), request.companyName())
                        .orElseThrow();
        //0 delete data
        //ProductionGroup foundPodGroup=groupRepository.findByName(request.prodName(), request.companyName());
        //1 delete group
        List<ProductionData> foundProdData =
                productionDataRepository.findAllProductionByCompanyName(request.prodName(), request.companyName())
                        .orElseThrow();
        productionDataRepository.deleteAll(foundProdData);
        //2 delete production
        productionRepository.delete(foundProduction);
        return new DeleteProductionResponse(true);
    }

    public GetProductionDataPackResponse getProductionDataPack(final GetProductionDataPackRequest request) {
        final String companyName = request.companyName();
        Production foundProduction =
                productionRepository.findAllProductionByCompanyName(request.productionName(), companyName)
                        .orElseThrow();
        List<ProductionData> foundData =
                productionDataRepository.getlast10Data(foundProduction.getName(), companyName).orElseThrow();
        return new GetProductionDataPackResponse(foundData.stream().map(ProductionData::toDTO).toList());
    }

    public ProductionDataReportResponse getProductionDataReport(final ProductionDataReportRequest request) {
        List<ProductionData> foundProdData =
                productionDataRepository.findAllProductionByCompanyName(request.productionName(), request.companyName())
                        .orElseThrow();

        return new ProductionDataReportResponse(foundProdData.stream().map(ProductionData::toDTO).toList());
    }

    public ProductionGroupResponse getProductionGroup(ProductionGroupRequest request) {
        List<ProductionGroup> foundGroups = groupRepository.findAllByName(request.companyName()).orElseThrow();
        Set<ElectricMeter> foundElectrics =
                electricMeterRepository.findAllElMetersByCompanyName(request.companyName()).orElseThrow();
        List<String> response = new ArrayList<>();
        foundElectrics.forEach(electricMeter -> response.add(electricMeter.getName()));
        return new ProductionGroupResponse(foundGroups.stream().map(ProductionGroup::getName).toList(), response);
    }

    public boolean deleteProductionDate(DeleteProductionDataRequest request) {
        ProductionData foundData = productionDataRepository.getDataById(request.id());
        productionDataRepository.delete(foundData);
        return true;
    }

    @Transactional
    public UpdateProductionResponse updateProd(UpdateProductionRequest request) {
        Company foundCompany = companyService.getCompanyFromName(request.companyName());
        Production foundProduction =
                productionRepository.findAllProductionByCompanyName(request.prodName(), request.companyName())
                        .orElseThrow();
        Set<ElectricMeter> electric =
                elMeterService.findAllElectricMeters(request.elMeterNames(), request.companyName());
        //productionRepository.delete(foundProduction);
        foundProduction.setName(request.prodNameNew());
        foundProduction.setDescription(request.prodDescription());
        foundProduction.getGroups().clear();
        foundProduction.getGroups().add(cascadeGroupFromName(request.groupName(), request.companyName(), foundCompany));
        foundProduction.getElectricMeters().clear();
        foundProduction.getElectricMeters().addAll(electric);
        foundProduction.setUnits(getUnitsFromName(request.unitsName()));

        productionRepository.save(foundProduction);
        return new UpdateProductionResponse(true);
    }
}
