package org.lci.volts.server.model.dto.production;


import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductionDataDTO {
    Long id;
    BigDecimal values;
    String date;
}
