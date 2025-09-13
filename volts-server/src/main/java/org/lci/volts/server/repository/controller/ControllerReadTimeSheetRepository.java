package org.lci.volts.server.repository.controller;

import org.lci.volts.server.persistence.controller.ControllerReadTimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ControllerReadTimeSheetRepository extends JpaRepository<ControllerReadTimeSheet, Long> {
    @Query("SELECT u FROM ControllerReadTimeSheet u WHERE u.company.name= ?1")
    Optional<ControllerReadTimeSheet> getControllerReadTimeSheetByCompanyName(String name);
}
