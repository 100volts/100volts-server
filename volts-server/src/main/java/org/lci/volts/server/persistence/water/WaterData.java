package org.lci.volts.server.persistence.water;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.water.WaterDataDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "water_data")
public class WaterData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "water_data_seq_gen")
    @SequenceGenerator(name = "water_data_seq_gen", sequenceName = "water_data_seq", allocationSize = 1)
    private Long id;
    private BigDecimal value;
    private Timestamp ts;
    @ManyToOne
    @JoinColumn(name = "water_meter")
    private Water water;

    public WaterDataDTO toDTO() {
        return new WaterDataDTO(value,ts.toLocalDateTime().toString());
    }
}
