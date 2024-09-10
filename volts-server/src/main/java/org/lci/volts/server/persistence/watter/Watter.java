package org.lci.volts.server.persistence.watter;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.model.dto.WatterDTO;
import org.lci.volts.server.model.dto.WatterDataDTO;
import org.lci.volts.server.persistence.Company;

import java.sql.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "watter")
public class Watter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "watter_name")
    private String name;
    @Column
    private String description;
    @Column
    private Date ts;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    public WatterDTO toDTO(List<WatterDataDTO> data) {
        return new WatterDTO(name,description,ts.toString(),data);
    }
}
