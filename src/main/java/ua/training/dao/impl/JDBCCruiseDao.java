package ua.training.dao.impl;

import ua.training.dao.CruiseDao;
import ua.training.dao.mapper.CruiseMapper;
import ua.training.dao.mapper.ObjectMapper;
import ua.training.dao.mapper.ShipMapper;
import ua.training.entity.Cruise;
import ua.training.entity.Ship;
import ua.training.exception.DBConnectionException;
import ua.training.persistance.ConnectionPoolHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.training.dao.TableConstants.*;

public class JDBCCruiseDao implements CruiseDao {

    private static final String FIND_ALL_QUERY = "SELECT * FROM " + CRUISES_TABLE + " INNER JOIN " + SHIPS_TABLE + " ON " + CRUISES_ID_COLUMN + " = " + SHIPS_ID_COLUMN + ";";
    private static final String FIND_BY_ID = "SELECT * FROM " + CRUISES_TABLE + " INNER JOIN " + SHIPS_TABLE + " ON " + CRUISES_SHIP_ID + " = " + SHIPS_ID_COLUMN + " WHERE " + CRUISES_ID_COLUMN + " = ?";
    private static final String UPDATE_CRUISE = "UPDATE " + CRUISES_TABLE + " SET " + CRUISES_NAME_COLUMN + " = ?, " +
            CRUISE_DESCRIPTION_ENG_COLUMN + " = ?, " + CRUISE_DESCRIPTION_RU_COLUMN + " = ?, " + CRUISE_DEPARTURE_DATE_COLUMN + " = ?, " + CRUISE_ARRIVAL_DATE + " = ? WHERE ID = ?";

    private final ConnectionPoolHolder connectionPoolHolder;
    private ObjectMapper<Cruise> cruiseMapper;
    private ObjectMapper<Ship> shipMapper;

    public JDBCCruiseDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
        this.cruiseMapper = new CruiseMapper();
        this.shipMapper = new ShipMapper();
    }

    @Override
    public Optional<Cruise> findById(long id) {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cruise cruise = cruiseMapper.extractFromResultSet(rs);
                cruise.setShip(shipMapper.extractFromResultSet(rs));
                return Optional.of(cruise);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Cruise> findAll() {
        List<Cruise> resultList = new ArrayList<>();
        try (Connection connection = connectionPoolHolder.getConnection();
             Statement pst = connection.createStatement()) {
            ResultSet rs = pst.executeQuery(FIND_ALL_QUERY);
            while (rs.next()) {
                Cruise cruise = cruiseMapper.extractFromResultSet(rs);
                cruise.setShip(shipMapper.extractFromResultSet(rs));
                resultList.add(cruise);
            }
            return resultList;
        } catch (SQLException ex) {
            throw new DBConnectionException(ex);
        }
    }

    @Override
    public boolean update(Cruise entity) {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_CRUISE)) {
            ps.setString(1, entity.getCruiseName());
            ps.setString(2, entity.getDescriptionEng());
            ps.setString(3, entity.getDescriptionRu());
            ps.setTimestamp(4, Timestamp.valueOf(entity.getDepartureDate().atStartOfDay()));
            ps.setTimestamp(5, Timestamp.valueOf(entity.getDepartureDate().atStartOfDay()));
            ps.setLong(6, entity.getId());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }


}
