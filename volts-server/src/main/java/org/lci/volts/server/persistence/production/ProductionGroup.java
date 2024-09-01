package org.lci.volts.server.persistence.production;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.electric.ElectricMeter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "production_group")
public class ProductionGroup {
    @Id
    private Long id;
    @Column(name = "production_group_name")
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;
    @ManyToMany
    @JoinTable(
            name = "electric_meter_production_group",
            joinColumns = @JoinColumn(name = "production_group"),
            inverseJoinColumns = @JoinColumn(name = "electric_meter")
    )
    private List<ElectricMeter> electricMeters;
}
