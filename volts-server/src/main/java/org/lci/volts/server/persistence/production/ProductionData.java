package org.lci.volts.server.persistence.production;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.production.ProductionDataDTO;
import org.lci.volts.server.model.dto.production.ProductionDataForKpiDTO;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Setter
@Getter
@Table(name = "production_data")
public class ProductionData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_data_seq_gen")
    @SequenceGenerator(name = "production_data_seq_gen", sequenceName = "production_data_seq", allocationSize = 1)
    private Long id;
    @Column
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "production")
    private Production production;
    @Column
    private Date ts;

    public ProductionDataDTO toDTO(){
        ProductionDataDTO dto = new ProductionDataDTO();
        dto.setDate(ts==null?null:ts.toString());
        dto.setValues(value);
        dto.setId(id);
        return dto;
    }

    public ProductionDataForKpiDTO toKpiDTO(){
        return new ProductionDataForKpiDTO(id,value,ts.toString(),production.toDto());
    }
}
