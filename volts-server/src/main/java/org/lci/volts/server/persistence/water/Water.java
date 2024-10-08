package org.lci.volts.server.persistence.water;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.water.WaterDTO;
import org.lci.volts.server.model.dto.water.WaterDataDTO;
import org.lci.volts.server.persistence.Company;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "water")
public class Water {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "water_name")
    private String name;
    @Column
    private String description;
    @Column
    private Timestamp ts;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;
}
