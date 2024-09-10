package org.lci.volts.server.model.responce.watter;

import org.lci.volts.server.model.dto.WatterDTO;

import java.util.List;

public record AllWatterForCompanyResponse(List<WatterDTO> watter) {
}
