package org.lci.volts.server.repository.production;

import org.lci.volts.server.persistence.production.ProductionKPI;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionKPIRepository extends JpaRepository<ProductionKPI, Long> {
}
