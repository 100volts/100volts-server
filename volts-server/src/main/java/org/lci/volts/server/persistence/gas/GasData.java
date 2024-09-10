package org.lci.volts.server.persistence.gas;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "gas_data")
public class GasData {
    @Id
    private Long id;
    private BigDecimal value;
    private Date ts;
    @ManyToOne
    @JoinColumn(name = "gas_meter")
    private Gas gas;
}
