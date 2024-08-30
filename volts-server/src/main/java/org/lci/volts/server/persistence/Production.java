package org.lci.volts.server.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "production")
public class Production {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="production_name")
    private String name;
    @Column
    private String description;
    @Column
    private Date ts;
    @ManyToOne
    @JoinColumn(name = "units")
    private Units units;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;
    @ManyToMany
    @JoinTable(
            name = "production_production_group",
            joinColumns = @JoinColumn(name = "production"),
            inverseJoinColumns = @JoinColumn(name = "production_group")
    )
    private List<ProductionGroup> groups;

    public ProductionDTO toDto() {
        return new ProductionDTO(name,description,ts.toString(),
                new UnitDTO(units.getName(), getUnits().getValue()),
                new CompanyDTO(company.getName()),
                getGroupDTOS()
        );
    }

    private List<GroupDTO> getGroupDTOS() {
        return groups.stream().map(group ->
                new GroupDTO(group.getName(), group.getDescription(),
                        group.getElectricMeters().stream()
                                .map(meter -> new ElMeterDTO(meter.getId().intValue(), meter.getAddress(), meter.getName())).toList())
        ).toList();
    }
}
