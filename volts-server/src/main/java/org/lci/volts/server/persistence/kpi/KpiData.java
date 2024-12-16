package org.lci.volts.server.persistence.kpi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.kpi.KPIDataDTO;
import org.lci.volts.server.persistence.production.Production;
import org.lci.volts.server.persistence.production.ProductionData;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "kpi_data")
public class KpiData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kpi_data_id_gen")
    @SequenceGenerator(name = "kpi_data_id_gen", sequenceName = "kpi_data_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value", nullable = false)
    private Float value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kpi", nullable = false)
    private Kpi kpi;

    @ManyToMany
    @JoinTable(
            name = "kpi_data_prod_data",
            joinColumns = @JoinColumn(name = "kpi_data"),
            inverseJoinColumns = @JoinColumn(name = "production_data")
    )
    private List<ProductionData> prodData;

    @Column(name = "ts", nullable = false)
    private OffsetDateTime ts;

    public KPIDataDTO toDTO() {
        return new KPIDataDTO(value,ts.toString(),prodData.stream().map(ProductionData::toKpiDTO).toList());
    }
}