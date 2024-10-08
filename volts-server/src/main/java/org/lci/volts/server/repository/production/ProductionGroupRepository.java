package org.lci.volts.server.repository.production;

import org.lci.volts.server.persistence.production.ProductionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductionGroupRepository extends JpaRepository<ProductionGroup, Long> {
    @Query("SELECT u FROM ProductionGroup u WHERE u.name= ?1 AND u.company.name= ?2 ORDER BY u.id ASC Limit 1")
    Optional<ProductionGroup> findByName(final String name,final String companyName);

    @Query("SELECT u FROM ProductionGroup u WHERE u.company.name= ?1 ORDER BY u.id ASC")
    Optional<List<ProductionGroup>> findAllByName(final String companyName);
}
