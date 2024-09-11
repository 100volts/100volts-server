package org.lci.volts.server.persistence.gas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lci.volts.server.persistence.Company;

import java.sql.Date;

@Entity
@Setter
@Getter
@Table(name = "gas")
public class Gas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "gas_name")
    private String name;
    @Column
    private String description;
    @Column
    private Date ts;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;
}
