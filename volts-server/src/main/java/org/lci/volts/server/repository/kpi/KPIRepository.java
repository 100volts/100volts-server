package org.lci.volts.server.repository.kpi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.lci.volts.server.persistence.kpi.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface KPIRepository extends JpaRepository<Kpi, Long> {
    @Query("select k from Kpi k where k.company.name= ?1 ")
    Optional<List<Kpi>> getKPIPackage(String companyName);

    @Query("select k from Kpi k where k.name=?1 and k.company.name= ?2 ")
    Optional<Kpi> findByNameAndCompany(String name, String companyName);

    @Query("SELECT k FROM Kpi k JOIN k.productions p WHERE p.name = :productionName")
    Optional<List<Kpi>> findByProductionName(@Param("productionName") String productionName);

}