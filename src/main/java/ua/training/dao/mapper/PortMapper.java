package ua.training.dao.mapper;

import ua.training.entity.Port;

import java.sql.ResultSet;
import java.sql.SQLException;
import static ua.training.dao.TableConstants.*;

public class PortMapper implements ObjectMapper<Port> {
    @Override
    public Port extractFromResultSet(ResultSet rs) throws SQLException {
        return Port.builder()
                .id((rs.getLong(PORTS_ID_COLUMN)))
                .portName(rs.getString(PORT_NAME_COLUMN))
                .build();
    }

}
