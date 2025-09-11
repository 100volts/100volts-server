package org.lci.volts.server.persistence.electric;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
@Table(name = "electric_meter_energy_data")
public class ElectricMeterEnergyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id", nullable = false)
    private ElectricMeter meter;

    @Column(name = "energy_active_import", nullable = false)
    private BigDecimal energyActiveImport;

    @Column(name = "energy_active_export", nullable = false)
    private BigDecimal energyActiveExport;

    @Column(name = "energy_reactive_import", nullable = false)
    private BigDecimal energyReactiveImport;

    @Column(name = "energy_reactive_export", nullable = false)
    private BigDecimal energyReactiveExport;

    @Column(name = "energy_apparent", nullable = false)
    private BigDecimal energyApparent;

    @Column(name = "recorded_at", nullable = false)
    private OffsetDateTime recordedAt = OffsetDateTime.now();
}
