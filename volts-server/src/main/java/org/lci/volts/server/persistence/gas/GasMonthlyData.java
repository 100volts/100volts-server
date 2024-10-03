package org.lci.volts.server.persistence.gas;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.gas.MonthlyGasMeterEnergyDTO;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Setter
@Getter
@Table(name = "gas_monthly_data")
public class GasMonthlyData {
    @Id
    private Long id;
    @Column(name = "value_meter")
    private BigDecimal valueMeter;
    @Column(name = "value_tariff")
    private BigDecimal valueTariff;
    @ManyToOne
    @JoinColumn(name = "gas_meter")
    private Gas gas;
    @Column(name = "ts")
    private Date ts;

    public final MonthlyGasMeterEnergyDTO toDTO(){
        return new MonthlyGasMeterEnergyDTO(ts.toLocalDate().getMonth(), valueMeter,valueTariff);
    }
}
