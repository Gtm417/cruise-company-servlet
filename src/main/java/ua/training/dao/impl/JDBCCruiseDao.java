package ua.training.dao.impl;

import ua.training.exception.DBConnectionException;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.dao.ConnectionPoolHolder;
import ua.training.dao.CruiseDao;
import ua.training.dao.mapper.CruiseMapper;
import ua.training.dao.mapper.ObjectMapper;
import ua.training.dao.mapper.ShipMapper;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ship;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCruiseDao implements CruiseDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM cruises inner join ships ON cruises.id = ships.id;";
    private static final String FIND_BY_ID = "SELECT * FROM cruises INNER JOIN ships ON cruises.ship_id = ships.id WHERE cruises.id = ?";
    private static final String UPDATE_CRUISE = "UPDATE cruises SET cruise_name = ?, " +
            "description_eng = ?, description_ru = ?, departure_date = ?, arrival_date = ? WHERE id = ?";
    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCCruiseDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public Optional<Cruise> findById(long id) {
        ObjectMapper<Cruise> cruiseMapper = new CruiseMapper();
        ObjectMapper<Ship> shipMapper = new ShipMapper();
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
        ObjectMapper<Cruise> cruiseMapper = new CruiseMapper();
        ObjectMapper<Ship> shipMapper = new ShipMapper();
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
