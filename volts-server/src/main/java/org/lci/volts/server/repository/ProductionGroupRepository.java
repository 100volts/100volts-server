package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.ProductionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionGroupRepository extends JpaRepository<ProductionGroup, Long> {
}
