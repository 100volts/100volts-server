package org.lci.volts.server.persistence.watter;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.WatterDataDTO;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "watter_data")
public class WatterData {
    @Id
    private Long id;
    private BigDecimal value;
    private Date ts;
    @ManyToOne
    @JoinColumn(name = "watter_meter")
    private Watter watter;

    public WatterDataDTO toDTO() {
        return new WatterDataDTO(value,ts.toString());
    }
}
