package org.lci.volts.server.model.dto;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductionDTO {
    private String name;
    private String description;
    private String dateOfCreation;
    private UnitDTO units;
    private CompanyDTO company;
    private List<GroupDTO> groups;
}
