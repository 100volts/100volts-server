package org.lci.volts.server.repository.production;

import org.lci.volts.server.persistence.production.ProductionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductionDataRepository extends JpaRepository<ProductionData, Long> {
    @Query("SELECT u FROM ProductionData u WHERE u.production.name= ?1 and u.production.company.name= ?2")
    Optional<List<ProductionData>> findAllProductionByCompanyName(final String prodName, final String company);
    @Query("SELECT u FROM ProductionData u WHERE u.production.company.name= ?1")
    Optional<List<ProductionData>> findAllByCompanyName(final String company);
    @Query("SELECT u FROM ProductionData u WHERE u.production.name= ?1 and u.production.company= ?2  ORDER BY u.id DESC Limit 6")
    Optional<List<ProductionData>> getlast10Data(final String prodName, final String company);
    @Query("SELECT e FROM ProductionData e WHERE  e.production= ?1 AND e.ts <= ?2 ORDER BY e.ts ASC")
    Optional<List<ProductionData>> getLast6Months(final Long prodId,final LocalDate sixMonthsAgo);
    @Query("SELECT e FROM ProductionData e WHERE  e.production.company.name= ?1 AND e.ts <= ?2 ORDER BY e.ts ASC")
    Optional<List<ProductionData>> getLast6MonthsForCompany(final String companyName,final LocalDate sixMonthsAgo);
    @Query("SELECT e FROM ProductionData e WHERE  e.id= ?1")
    ProductionData getDataById(Long id);
}
