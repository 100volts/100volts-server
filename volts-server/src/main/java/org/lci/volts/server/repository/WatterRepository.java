package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.watter.Watter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WatterRepository extends JpaRepository<Watter, Long> {
    @Query("SELECT u FROM Watter u WHERE u.company.name= ?1 ORDER BY u.ts ASC ")
    Optional<List<Watter>> getAllWatterForCompany(String companyName);
}
