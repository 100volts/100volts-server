package org.lci.volts.server.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "connector_error_report")
public class ConnectorReports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String content;
    @Column(name="ts")
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;
}
