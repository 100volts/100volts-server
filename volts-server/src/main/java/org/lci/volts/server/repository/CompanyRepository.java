package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT u FROM Company u WHERE u.name= ?1")
    Optional<Company> findByName(String email);
}
