package org.lci.volts.server.repository;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.dto.ElMeterDTO;
import org.lci.volts.server.model.dto.ElMeterDataDTO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@RequiredArgsConstructor
public class ElMeterRpository {
    private final DataSource dataSource;

    public Boolean saveElmeterData(ElMeterDataDTO elmeterData) {
        final String insertData = """
                   INSERT INTO public.electric_meter_data (
                   id, el_meter, voltage_l1_n, voltage_l2_n, voltage_l3_n,
                   current_l1, current_l2, current_l3, active_power_l1,
                   active_power_l2, active_power_l3, power_factor_l1,
                   power_factor_l2, power_factor_l3, total_active_power,
                   total_active_energy_import_tariff_1,
                   total_active_energy_import_tariff_2) 
                   VALUES(?, ?, ?, ?, ?,?, ?, ?, ?,?, ?, ?,?, ?, ?,?, ?);
                """;
        boolean result = false;
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertData)) {
            preparedStatement.setInt(1, getLastIdMerData() + 1);
            preparedStatement.setBigDecimal(2, elmeterData.getMerterId());
            preparedStatement.setBigDecimal(3, elmeterData.getVoltagell1());
            preparedStatement.setBigDecimal(4,elmeterData.getVoltagell2());
            preparedStatement.setBigDecimal(5,elmeterData.getVoltagell3());
            preparedStatement.setBigDecimal(6,elmeterData.getCurrentl1());
            preparedStatement.setBigDecimal(7,elmeterData.getCurrentl2());
            preparedStatement.setBigDecimal(8,elmeterData.getCurrentl3());
            preparedStatement.setBigDecimal(9,elmeterData.getActivepowerl1());
            preparedStatement.setBigDecimal(10,elmeterData.getActivepowerl2());
            preparedStatement.setBigDecimal(11,elmeterData.getActivepowerl3());
            preparedStatement.setBigDecimal(12,elmeterData.getPfl1());
            preparedStatement.setBigDecimal(13,elmeterData.getPfl2());
            preparedStatement.setBigDecimal(14,elmeterData.getPfl3());
            preparedStatement.setBigDecimal(15,elmeterData.getTotalActivePpower());
            preparedStatement.setBigDecimal(16,elmeterData.getTotalActiveEnergyImportTariff1());
            preparedStatement.setBigDecimal(17,elmeterData.getTotalActiveEnergyImportTariff2());

            result = preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {

                DataSourceUtils.doReleaseConnection(connection, dataSource);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return !result;
    }

    public boolean createElmeter(ElMeterDTO elmeterData) {
        final String insertData = """
                INSERT INTO electric_meter(id, company,el_meter_address, el_meter_name) VALUES(?,?,?,?) RETURNING id;
                """;
        boolean result = false;
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertData)) {
            preparedStatement.setInt(1, getLastId() + 1);
            preparedStatement.setInt(2, elmeterData.getCompanyId());
            preparedStatement.setInt(3, elmeterData.getMeterAddress());
            preparedStatement.setString(4, elmeterData.getMeterName());

            result = preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {

                DataSourceUtils.doReleaseConnection(connection, dataSource);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return result;
    }

    private int getLastId() {
        final String getId = "SELECT id as id_found FROM electric_meter ORDER BY id DESC LIMIT 1;";

        Connection connection = DataSourceUtils.getConnection(dataSource);
        int result = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(getId)) {
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("id_found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DataSourceUtils.doReleaseConnection(connection, dataSource);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return result;
    }

    private int getLastIdMerData() {
        final String getId = "SELECT id as id_found FROM electric_meter_data ORDER BY id DESC LIMIT 1;";

        Connection connection = DataSourceUtils.getConnection(dataSource);
        int result = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(getId)) {
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("id_found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DataSourceUtils.doReleaseConnection(connection, dataSource);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return result;
    }
}
