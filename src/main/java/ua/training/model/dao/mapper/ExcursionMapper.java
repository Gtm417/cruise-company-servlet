package ua.training.model.dao.mapper;

import ua.training.model.entity.Excursion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ExcursionMapper implements ObjectMapper<Excursion> {
    @Override
    public Excursion extractFromResultSet(ResultSet rs) throws SQLException {
        return Excursion.builder()
                .id(rs.getLong("excursions.id"))
                .excursionName(rs.getString("excursion_name"))
                .duration(rs.getInt("duration"))
                .price(rs.getLong("price"))
                .build();
    }

    @Override
    public Excursion makeUnique(Map<Long, Excursion> cache, Excursion entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
