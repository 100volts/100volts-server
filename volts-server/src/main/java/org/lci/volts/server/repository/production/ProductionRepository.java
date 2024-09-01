package org.lci.volts.server.repository.production;

import org.lci.volts.server.persistence.production.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    @Query("SELECT u FROM Production u WHERE u.name= ?1 AND u.company.name= ?2 ORDER BY u.id ASC Limit 1")
    Optional<Production> findAllProductionByCompanyName(final String name, final String companyName);
    @Query("SELECT u FROM Production u WHERE u.company.name= ?1 ORDER BY u.name ASC")
    Optional<List<Production>> findAllProductionsAllCompanyName(final String companyName);
}
