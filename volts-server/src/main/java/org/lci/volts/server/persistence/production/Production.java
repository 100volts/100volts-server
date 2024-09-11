package org.lci.volts.server.persistence.production;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.*;
import org.lci.volts.server.model.dto.electricity.ElMeterDTO;
import org.lci.volts.server.model.dto.electricity.MonthValueDTO;
import org.lci.volts.server.model.dto.production.*;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.electric.ElectricMeter;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "production")
public class Production {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "production_name")
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
    @ManyToMany
    @JoinTable(
            name = "electric_meter_production",
            joinColumns = @JoinColumn(name = "production"),
            inverseJoinColumns = @JoinColumn(name = "electric_meter")
    )
    private Set<ElectricMeter> electricMeters;

    public ProductionDTO toDto() {
        return new ProductionDTO(name, description, ts.toString(),
                new UnitDTO(units.getName(), getUnits().getValue()),
                new CompanyDTO(company.getName()),
                getGroupDTOS(),
                electricMeters.stream().map(electricMeter ->
                        new ElMeterDTO(electricMeter.getId().intValue(), electricMeter.getAddress(), electricMeter.getName())
                ).toList()
        );
    }

    public ProductionPackageDTO toPackageDTO(final List<MonthValueDTO> monthlyData, final List<ProductionDataDTO> last10) {
        return new ProductionPackageDTO(name, description, ts.toString(),
                new UnitDTO(units.getName(), getUnits().getValue()),
                new CompanyDTO(company.getName()),
                getGroupDTOS(),
                electricMeters.stream().map(electricMeter ->
                        new ElMeterDTO(electricMeter.getId().intValue(), electricMeter.getAddress(), electricMeter.getName())
                ).toList(),
                monthlyData,last10
        );
    }

    private List<GroupDTO> getGroupDTOS() {
        return groups.stream().map(group ->
                new GroupDTO(group.getName(), group.getDescription())).toList();
    }
}
