package ua.training.dao.impl;

import ua.training.dao.ExcursionDao;
import ua.training.dao.mapper.ExcursionMapper;
import ua.training.dao.mapper.ObjectMapper;
import ua.training.dao.mapper.PortMapper;
import ua.training.entity.Excursion;
import ua.training.entity.Port;
import ua.training.exception.DBConnectionException;
import ua.training.persistance.ConnectionPoolHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.training.dao.TableConstants.*;


public class JDBCExcursionDao implements ExcursionDao {
    private final static String FIND_ALL_EXCURSION_FOR_CRUISE = "SELECT " +
            EXCURSIONS_ID_COLUMN + ", " +
            EXCURSION_NAME_COLUMN + ", " +
            PRICE_COLUMN + "," +
            EXCURSIONS_DURATION_COLUMN + ", " +
            PORTS_ID_COLUMN + ", " +
            PORT_NAME_COLUMN +
            " FROM " + PORTS_CRUISES_TABLE  +
            " inner join " + EXCURSIONS_TABLE + " ON " + PORTS_CRUISES_PORT_ID_COLUMN + " = " + EXCURSIONS_PORT_ID_COLUMN +
            " inner join " + PORTS_TABLE + " ON " + PORTS_CRUISES_PORT_ID_COLUMN + " = " + PORTS_ID_COLUMN +
            " WHERE " + PORTS_CRUISES_CRUISE_ID_COLUMN + " = ?";
    private final static String FIND_EXCURSION_BY_ID = "SELECT * FROM " + EXCURSIONS_TABLE +
            " INNER JOIN " + PORTS_TABLE + " ON " + EXCURSIONS_PORT_ID_COLUMN + " = " + PORTS_ID_COLUMN +
            " WHERE (" + EXCURSIONS_ID_COLUMN + " = ?)";

    private final ConnectionPoolHolder connectionPoolHolder;
    private ObjectMapper<Excursion> excursionMapper;
    private ObjectMapper<Port> portMapper;

    public JDBCExcursionDao(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
        this.excursionMapper =  new ExcursionMapper();
        this.portMapper = new PortMapper();
    }

    @Override
    public Optional<Excursion> findById(long id) {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_EXCURSION_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Excursion excursion = excursionMapper.extractFromResultSet(rs);
                excursion.setPort(portMapper.extractFromResultSet(rs));
                return Optional.of(excursion);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Excursion> findAllExcursionsByCruiseId(long cruiseId) {
        List<Excursion> excursions = new ArrayList<>();

        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_EXCURSION_FOR_CRUISE)) {
            ps.setLong(1, cruiseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Excursion excursion = excursionMapper.extractFromResultSet(rs);
                Port port = portMapper.extractFromResultSet(rs);
                excursion.setPort(port);
                excursions.add(excursion);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return excursions;
    }

}
