package org.lci.volts.server.model.request.production;

public record CreteProductionRequest(String prodName, String prodDescription, String companyName, String unitsName,String groupName,String[] elMeterNames ) {
}
