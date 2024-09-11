package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.gas.GasData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GasDataRepository extends JpaRepository<GasData, Long> {
    @Query("SELECT u FROM GasData u WHERE u.gas.company.name= ?1 ORDER BY u.ts ASC ")
    Optional<List<GasData>> getAllGasDataForCompany(String companyName);
}