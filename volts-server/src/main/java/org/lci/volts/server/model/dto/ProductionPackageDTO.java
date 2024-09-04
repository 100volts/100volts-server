package org.lci.volts.server.model.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductionPackageDTO {
    private String name;
    private String description;
    private String dateOfCreation;
    private UnitDTO units;
    private CompanyDTO company;
    private List<GroupDTO> groups;
    private List<ElMeterDTO> electricMeters;
    private List<MonthValueDTO> monthlyData;
}
