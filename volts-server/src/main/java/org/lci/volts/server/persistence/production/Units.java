package org.lci.volts.server.persistence.production;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "unit")
public class Units {
    @Id
    private Long id;
    @Column(name="unit_name")
    private String name;
    @Column(name="unit_value")
    private String value;
}
