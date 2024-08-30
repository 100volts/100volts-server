package org.lci.volts.server.model.dto;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupDTO {
    private String name;
    private String description;
    private List<ElMeterDTO> electricMeters;
}
