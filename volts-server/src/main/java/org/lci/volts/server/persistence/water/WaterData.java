package org.lci.volts.server.persistence.water;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.water.WaterDataDTO;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "water_data")
public class WaterData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal value;
    private Date ts;
    @ManyToOne
    @JoinColumn(name = "water_meter")
    private Water water;

    public WaterDataDTO toDTO() {
        return new WaterDataDTO(value,ts.toString());
    }
}
