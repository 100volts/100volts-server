package org.lci.volts.server.persistence.electric;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.persistence.Company;


@Entity
@Setter
@Getter
@Table(name = "electric_meter")
public class ElectricMeter {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;
    @Column(name="el_meter_name")
    private String name;
    @Column(name = "el_meter_address")
    private int address;
}
