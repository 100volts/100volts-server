package org.lci.volts.server.repository;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.ElMeterDTO;
import org.lci.volts.server.model.ElMeterDataDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ElMeterRpository {
    private final DataSource dataSource;

    Boolean saveElmeterData(ElMeterDataDTO elmeterData) {
        final String insertData = """
                    INSERT INTO elmeter VALUES ()
                """;


        return true;
    }

    public boolean createElmeter(ElMeterDTO elmeterData) {
        final String insertData = """
                INSERT INTO electric_meter(id, company,el_meter_address, el_meter_name) VALUES(?,?,?,?) RETURNING id;
                """;
        boolean result = false;
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertData)) {
            preparedStatement.setInt(1, getLastId()+1);
            preparedStatement.setInt(2, elmeterData.getCompanyId());
            preparedStatement.setInt(3, elmeterData.getMeterAddress());
            preparedStatement.setString(4, elmeterData.getMeterName());

            result = preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
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
            var resultSet=preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("id_found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                DataSourceUtils.doReleaseConnection(connection, dataSource);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return result;
    }
}
