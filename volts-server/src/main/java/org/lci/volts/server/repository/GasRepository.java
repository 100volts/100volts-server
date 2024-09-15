package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.gas.Gas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface GasRepository extends JpaRepository<Gas, Long> {
    @Query("SELECT u FROM Gas u WHERE u.company.name= ?1 ORDER BY u.ts ASC ")
    Optional<List<Gas>> getAllGasForCompany(final String companyName);
    @Query("SELECT u FROM Gas u WHERE u.company.name= ?1 AND u.name=?2 ORDER BY u.ts ASC ")
    Optional<Gas> getGasByNameAndCompanyName(final String companyName, final String name);
    @Query("SELECT u FROM Gas u WHERE u.company.name= ?1 AND u.ts> ?2 AND u.ts< ?3 ORDER BY u.ts ASC ")
    Optional<List<Gas>> getAllGasForCompanyForData(final String companyName, final Date start, final Date end);
}
