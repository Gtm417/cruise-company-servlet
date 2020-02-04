package ua.training.model.dao.mapper;

import ua.training.model.entity.Port;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PortMapper implements ObjectMapper<Port>{
    @Override
    public Port extractFromResultSet(ResultSet rs) throws SQLException {
       return Port.builder()
               .id((rs.getLong("ports.id")))
               .portName(rs.getString("port_name"))
               .build();
    }

    @Override
    public Port makeUnique(Map<Long, Port> cache, Port entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
