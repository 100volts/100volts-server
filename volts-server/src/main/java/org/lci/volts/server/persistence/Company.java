package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "company")
public class Company {
    @Id
    public Long id;

    @Column()
    public String name;

    //@OneToMany(mappedBy = "company")
    //private List<CompanyUser> books;

    public Company() {
    }
}