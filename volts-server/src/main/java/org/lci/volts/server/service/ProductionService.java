package org.lci.volts.server.service;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.production.GetProductionRequest;
import org.lci.volts.server.model.responce.production.GetProductionResponse;
import org.lci.volts.server.persistence.Production;
import org.lci.volts.server.repository.ProductionRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ReadingConverter
@RequiredArgsConstructor
public class ProductionService {
    private final ProductionRepository productionRepository;
    public GetProductionResponse getProdByName(final GetProductionRequest request) {
        return new GetProductionResponse(
                productionRepository.findAllElMetersByCompanyName(request.name(), request.companyName()).orElseThrow().toDto());
    }
}
