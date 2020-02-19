package ua.training.dao.mapper;

import ua.training.entity.Cruise;

import java.sql.ResultSet;
import java.sql.SQLException;
import static ua.training.dao.TableConstants.*;

public class CruiseMapper implements ObjectMapper<Cruise> {

    @Override
    public Cruise extractFromResultSet(ResultSet rs) throws SQLException {
        return Cruise.builder().id(rs.getInt(CRUISES_ID_COLUMN))
                .name(rs.getString(CRUISES_NAME_COLUMN))
                .departureDate(rs.getTimestamp(CRUISE_DEPARTURE_DATE_COLUMN).toLocalDateTime().toLocalDate())
                .arrivalDate(rs.getTimestamp(CRUISE_ARRIVAL_DATE).toLocalDateTime().toLocalDate())
                .descriptionEng(rs.getString(CRUISE_DESCRIPTION_ENG_COLUMN))
                .descriptionRu(rs.getString(CRUISE_DESCRIPTION_RU_COLUMN))
                .build();
    }

}
