package org.lci.volts.server.repository.electric;

import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ElectricMeterRepository extends JpaRepository<ElectricMeter, Long> {
    @Query("SELECT u FROM ElectricMeter u WHERE u.address= ?1")
    Optional<ElectricMeter> findByAddress(final int address);

    @Query("SELECT u FROM ElectricMeter u WHERE u.company.name= ?1 ORDER BY u.address ASC ")
    Optional<Set<ElectricMeter>> findAllElMetersByCompanyName(final String name);

    @Query("SELECT u FROM ElectricMeter u WHERE u.company.name= ?2 and u.address= ?1 ORDER BY u.address ASC limit 1 ")
    Optional<ElectricMeter> findElMetersByCompanyName(final int address,final String companyName);

    @Query("SELECT u FROM ElectricMeter u WHERE u.name= ?1 AND u.company.name= ?2  ORDER BY u.address ASC limit 1")
    Optional<ElectricMeter> findAllElMetersByCompanyNameAndNAme(final String name,final String companyName);
}
