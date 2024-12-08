package org.lci.volts.server.persistence.kpi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.lci.volts.server.model.dto.kpi.KPIDTO;
import org.lci.volts.server.persistence.Company;
import org.lci.volts.server.persistence.Energy;
import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.production.Production;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "kpi")
public class Kpi {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kpi_id_gen")
    @SequenceGenerator(name = "kpi_id_gen", sequenceName = "kpi_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "descriptor", nullable = false, length = 200)
    private String descriptor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "energy", nullable = false)
    private Energy energy;

    @Column(name = "ts", nullable = false)
    private OffsetDateTime ts;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settings", nullable = false)
    private KpiSetting settings;

    @Column(name = "target", nullable = false)
    private Double target;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_kpi", nullable = false)
    private KpiGroup groupKpi;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "kpi_production",
            joinColumns = @JoinColumn(name = "kpi"),
            inverseJoinColumns = @JoinColumn(name = "production")
    )
    private List<Production> productions;

    public KPIDTO toDTO() {
        return new KPIDTO(name,descriptor,groupKpi.getName(),target.toString(),energy.toDTO(), productions.stream().map(Production::toDto).toList(),ts.toString());
    }
}