package ua.training.model.dao.mapper;

import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ship;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ShipMapper implements ObjectMapper<Ship> {
    @Override
    public Ship extractFromResultSet(ResultSet rs) throws SQLException {
        return Ship.builder().id(rs.getInt("id"))
                .name(rs.getString("ship_name"))
                .currentPassengerAmount(rs.getInt("current_amount_of_passenger"))
                .maxPassengerAmount(rs.getInt("max_amount_of_passenger"))
                .build();
    }

    @Override
    public Ship makeUnique(Map<Long, Ship> cache, Ship entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
