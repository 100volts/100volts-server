package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.watter.WatterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WatterDataRepository extends JpaRepository<WatterData, Long> {
    @Query("SELECT u FROM WatterData u WHERE u.watter.company.name= ?1 ORDER BY u.ts ASC ")
    Optional<List<WatterData>> getAllWatterDataForCompany(String companyName);
}
