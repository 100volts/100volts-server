package org.lci.volts.server.model.request.kpi;

import java.sql.Date;

public record KPIUpdateByDateRequest(String company,  String kpiName,String date) {
}
