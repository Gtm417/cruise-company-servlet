package ua.training.dao.mapper;

import ua.training.entity.Excursion;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}
