package ua.training.dao.mapper;

import ua.training.entity.Ship;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipMapper implements ObjectMapper<Ship> {
    @Override
    public Ship extractFromResultSet(ResultSet rs) throws SQLException {
        return Ship.builder().id(rs.getInt("ships.id"))
                .name(rs.getString("ship_name"))
                .currentPassengerAmount(rs.getInt("current_amount_of_passenger"))
                .maxPassengerAmount(rs.getInt("max_amount_of_passenger"))
                .build();
    }

}
