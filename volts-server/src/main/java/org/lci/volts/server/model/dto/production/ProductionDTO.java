package org.lci.volts.server.model.dto.production;

import lombok.*;
import org.lci.volts.server.model.dto.CompanyDTO;
import org.lci.volts.server.model.dto.electricity.ElMeterDTO;

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
    private List<ElMeterDTO> electricMeters;
}
