package ua.training.dao.mapper;

import ua.training.entity.Cruise;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CruiseMapper implements ObjectMapper<Cruise> {

    @Override
    public Cruise extractFromResultSet(ResultSet rs) throws SQLException {
        return Cruise.builder().id(rs.getInt("cruises.id"))
                .name(rs.getString("cruise_name"))
                .departureDate(rs.getTimestamp("departure_date").toLocalDateTime().toLocalDate())
                .arrivalDate(rs.getTimestamp("arrival_date").toLocalDateTime().toLocalDate())
                .descriptionEng(rs.getString("description_eng"))
                .descriptionRu(rs.getString("description_ru"))
                .build();
    }

}
