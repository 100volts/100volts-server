package org.lci.volts.server.persistence.electric;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.electricity.ElMeterDTO;
import org.lci.volts.server.persistence.Company;


@Entity
@Setter
@Getter
@Table(name = "electric_meter")
public class ElectricMeter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "company")
    private Company company;
    @Column(name="el_meter_name")
    private String name;
    @Column(name = "el_meter_address")
    private int address;
    @Column(name="read_time_gap")
    private int readTimeGap;

    public ElMeterDTO toDTO() {
        return new ElMeterDTO(0,id.intValue(),name);
    }
}
