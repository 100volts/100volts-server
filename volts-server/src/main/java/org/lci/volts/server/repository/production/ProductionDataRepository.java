package org.lci.volts.server.repository.production;

import org.lci.volts.server.persistence.ProductionData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionDataRepository extends JpaRepository<ProductionData, Long> {
}
