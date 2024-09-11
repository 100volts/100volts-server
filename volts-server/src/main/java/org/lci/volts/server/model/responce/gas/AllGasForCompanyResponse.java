package org.lci.volts.server.model.responce.gas;

import org.lci.volts.server.model.dto.gas.GasDTO;

import java.util.List;

public record AllGasForCompanyResponse(List<GasDTO> gas) {
}
