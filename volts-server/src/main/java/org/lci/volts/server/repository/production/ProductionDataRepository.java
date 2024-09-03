package org.lci.volts.server.repository.production;

import org.lci.volts.server.persistence.production.ProductionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductionDataRepository extends JpaRepository<ProductionData, Long> {
    @Query("SELECT u FROM ProductionData u WHERE u.production= ?1")
    Optional<List<ProductionData>> findAllProductionByCompanyName(final Long prodId);
}
