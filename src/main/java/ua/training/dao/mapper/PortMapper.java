package ua.training.dao.mapper;

import ua.training.entity.Port;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PortMapper implements ObjectMapper<Port> {
    @Override
    public Port extractFromResultSet(ResultSet rs) throws SQLException {
        return Port.builder()
                .id((rs.getLong("ports.id")))
                .portName(rs.getString("port_name"))
                .build();
    }

}
