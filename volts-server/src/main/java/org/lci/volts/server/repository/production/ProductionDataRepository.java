package org.lci.volts.server.repository.production;

import org.lci.volts.server.persistence.production.ProductionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductionDataRepository extends JpaRepository<ProductionData, Long> {
    @Query("SELECT u FROM ProductionData u WHERE u.production= ?1")
    Optional<List<ProductionData>> findAllProductionByCompanyName(final Long prodId);
    @Query("SELECT u FROM ProductionData u WHERE u.production= ?1  ORDER BY u.id DESC Limit 6")
    Optional<List<ProductionData>> getlast10Data(final Long prodId);
    @Query("SELECT e FROM ProductionData e WHERE  e.production= ?1 AND e.ts <= ?2 ORDER BY e.ts ASC")
    Optional<List<ProductionData>> getLast6Months(final Long prodId,final LocalDate sixMonthsAgo);
    @Query("SELECT e FROM ProductionData e WHERE  e.id= ?1")
    ProductionData getDataById(Long id);
}
