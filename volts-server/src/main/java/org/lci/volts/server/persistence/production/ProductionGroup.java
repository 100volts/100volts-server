package org.lci.volts.server.persistence.production;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lci.volts.server.persistence.Company;

@Entity
@Setter
@Getter
@Table(name = "production_group")
@AllArgsConstructor
@NoArgsConstructor
public class ProductionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_group_seq_gen")
    @SequenceGenerator(name = "production_group_seq_gen", sequenceName = "production_group_seq", allocationSize = 1)
    private Long id;
    @Column(name = "production_group_name")
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

}
