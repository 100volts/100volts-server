package org.lci.volts.server.persistence.kpi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.kpi.KPIGroupDTO;
import org.lci.volts.server.persistence.Company;

@Getter
@Setter
@Entity
@Table(name = "kpi_group")
public class KpiGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kpi_group_id_gen")
    @SequenceGenerator(name = "kpi_group_id_gen", sequenceName = "kpi_group_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "descriptor", nullable = false, length = 200)
    private String descriptor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company")
    private Company company;

    public KPIGroupDTO toDto() {
        return new KPIGroupDTO(name, descriptor);
    }
}