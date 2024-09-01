package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.ProductionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductionGroupRepository extends JpaRepository<ProductionGroup, Long> {
    @Query("SELECT u FROM ProductionGroup u WHERE u.name= ?1 AND u.company.name= ?2 ORDER BY u.id ASC Limit 1")
    Optional<ProductionGroup> findByName(final String name,final String companyName);
}
