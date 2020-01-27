package ua.training.model.dao.mapper;

import ua.training.model.entity.Cruise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CruiseMapper implements ObjectMapper<Cruise>{

    @Override
    public Cruise extractFromResultSet(ResultSet rs) throws SQLException {
        return Cruise.builder().id(rs.getInt("id"))
                .name(rs.getString("cruiseName"))
                .departureDate(rs.getTimestamp("departureDate").toLocalDateTime().toLocalDate())
                .arrivalDate(rs.getTimestamp("arrivalDate").toLocalDateTime().toLocalDate())
                .descriptionEng(rs.getString("description_eng"))
                .descriptionEng(rs.getString("description_ru"))
                .build();
    }

    @Override
    public Cruise makeUnique(Map<Long, Cruise> cache,
                              Cruise cruise) {
        cache.putIfAbsent(cruise.getId(), cruise);
        return cache.get(cruise.getId());
    }
}
