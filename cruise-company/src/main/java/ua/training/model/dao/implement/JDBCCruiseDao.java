package ua.training.model.dao.implement;

import ua.training.exception.DuplicateDataBaseException;
import ua.training.model.dao.ConnectionPoolHolder;
import ua.training.model.dao.CruiseDao;
import ua.training.model.dao.mapper.CruiseMapper;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.dao.mapper.ShipMapper;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ship;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCruiseDao implements CruiseDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM cruises inner join ships ON cruises.id = ships.id;";
    private static final String FIND_BY_ID = "SELECT * FROM cruises  WHERE id = ?";
    private static final String UPDATE_CRUISE = "UPDATE cruises SET cruise_name = ?, " +
            "description_eng = ?, description_ru = ?, departure_date = ?, arrival_date = ? WHERE id = ?";
    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCCruiseDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(Cruise entity) throws DuplicateDataBaseException {
        return false;
    }

    @Override
    public Optional<Cruise> findById(long id) {
        ObjectMapper<Cruise> cruiseMapper = new CruiseMapper();

        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(cruiseMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            //todo throw new runtime DBException handling in web.xml
            e.printStackTrace();
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
            ex.printStackTrace();
            throw new RuntimeException();
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
            //todo throw db Exception runtime status 500 for user
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(int id) {

    }

}
