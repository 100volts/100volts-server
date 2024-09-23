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
    @SequenceGenerator(name = "electric_meter_SEQ",
            sequenceName = "electric_meter_SEQ",
            allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;
    @Column(name="el_meter_name")
    private String name;
    @Column(name = "el_meter_address")
    private int address;
    @Column(name="read_time_gap")
    private int readTimeGap;
}
