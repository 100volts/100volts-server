package org.lci.volts.server.persistence.gas;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.gas.GasDataDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "gas_data")
public class GasData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gas_data_seq_gen")
    @SequenceGenerator(name = "gas_data_seq_gen", sequenceName = "gas_data_seq", allocationSize = 1)
    private Long id;
    private BigDecimal value;
    private Timestamp ts;
    @ManyToOne
    @JoinColumn(name = "gas_meter")
    private Gas gas;

    public GasDataDTO toDTO() {
        return new GasDataDTO(value,ts.toLocalDateTime().toString());
    }
}
