package org.lci.volts.server.model.dto;


import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductionDataDTO {
    BigDecimal values;
    String date;
}
