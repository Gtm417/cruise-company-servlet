package ua.training.dao.mapper;

import ua.training.entity.Excursion;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.training.dao.TableConstants.*;

public class ExcursionMapper implements ObjectMapper<Excursion> {
    @Override
    public Excursion extractFromResultSet(ResultSet rs) throws SQLException {
        return Excursion.builder()
                .id(rs.getLong(EXCURSIONS_ID_COLUMN))
                .excursionName(rs.getString(EXCURSION_NAME_COLUMN))
                .duration(rs.getInt(EXCURSIONS_DURATION_COLUMN))
                .price(rs.getLong(PRICE_COLUMN))
                .build();
    }

}
