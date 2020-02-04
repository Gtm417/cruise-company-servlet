package ua.training.model.dao.mapper;

import ua.training.model.entity.Cruise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CruiseMapper implements ObjectMapper<Cruise>{

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

    @Override
    public Cruise makeUnique(Map<Long, Cruise> cache,
                              Cruise cruise) {
        cache.putIfAbsent(cruise.getId(), cruise);
        return cache.get(cruise.getId());
    }
}
