package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.EnergyDTO;
import org.lci.volts.server.persistence.electric.ElectricMeter;
import org.lci.volts.server.persistence.production.ProductionGroup;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "energy")
public class Energy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "energy_id_gen")
    @SequenceGenerator(name = "energy_id_gen", sequenceName = "energy_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "energy_index", nullable = false)
    private Double energyIndex;

    @Column(name = "ts", nullable = false)
    private OffsetDateTime ts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "energy_electric",
            joinColumns = @JoinColumn(name = "energy"),
            inverseJoinColumns = @JoinColumn(name = "electric")
    )
    private List<ElectricMeter> electricMeters;

    public EnergyDTO toDTO() {
        return new EnergyDTO(energyIndex.toString(),electricMeters.stream().map(electricMeter -> electricMeter.toDTO()).toList());
    }
}