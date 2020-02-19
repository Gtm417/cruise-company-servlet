package ua.training.dao.mapper;

import ua.training.entity.Ship;

import java.sql.ResultSet;
import java.sql.SQLException;
import static ua.training.dao.TableConstants.*;

public class ShipMapper implements ObjectMapper<Ship> {
    @Override
    public Ship extractFromResultSet(ResultSet rs) throws SQLException {
        return Ship.builder().id(rs.getInt(SHIPS_ID_COLUMN))
                .name(rs.getString(SHIPS_NAME_COLUMN))
                .currentPassengerAmount(rs.getInt(SHIP_CURRENT_AMOUNT_OF_PASSENGER_COLUMN))
                .maxPassengerAmount(rs.getInt(SHIP_MAX_AMOUNT_OF_PASSENGER_COLUMN))
                .build();
    }

}
