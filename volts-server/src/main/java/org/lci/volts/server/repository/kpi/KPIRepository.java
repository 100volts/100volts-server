package org.lci.volts.server.repository.kpi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.lci.volts.server.persistence.kpi.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface KPIRepository extends JpaRepository<Kpi, Long> {
    @Query("select k from Kpi k where k.company.name= ?1 ")
    Optional<List<Kpi>> getKPIPackage(String companyName);
}
/*
@Repository
@RequiredArgsConstructor
@Transactional
public class KPIRepository {
    private final EntityManagerFactory emf;

    public Optional getKPIPackage(String companyName){
        EntityManager entityManager = emf.createEntityManager();
        Query query = entityManager
                .createQuery("select Kpi from Kpi " +
                        "where Kpi.production.company.name= :companyName ");
        query.setParameter("companyName", companyName);

        return Optional.ofNullable(query.getResultList());
    }
}

 */
