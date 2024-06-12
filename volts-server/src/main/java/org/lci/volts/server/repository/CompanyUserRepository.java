package org.lci.volts.server.repository;

import org.lci.volts.server.persistence.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    @Query("SELECT u FROM CompanyUser u WHERE u.email= ?1")
    Optional<CompanyUser> findByEmail(String email);}
