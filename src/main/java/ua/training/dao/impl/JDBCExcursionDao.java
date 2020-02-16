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
import java.util.Set;

public class JDBCExcursionDao implements ExcursionDao {
    private final static String FIND_ALL_EXCURSION_FOR_CRUISE = "SELECT excursions.id,excursion_name,price,duration, ports.id, port_name FROM ports_cruises " +
            "inner join excursions ON ports_cruises.port_id = excursions.port_id " +
            "inner join ports ON ports_cruises.port_id = ports.id " +
            "WHERE cruise_id = ?";
    private final static String FIND_EXCURSION_BY_ID = "SELECT * FROM excursions " +
            "INNER JOIN ports ON excursions.port_id = ports.id " +
            "WHERE (excursions.id = ?)";

    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCExcursionDao(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public Optional<Excursion> findById(long id) {
        ObjectMapper<Excursion> excursionMapper = new ExcursionMapper();
        ObjectMapper<Port> portMapper = new PortMapper();
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
        ObjectMapper<Excursion> excursionMapper = new ExcursionMapper();
        ObjectMapper<Port> portMapper = new PortMapper();
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

    @Override
    public void saveExcursionsToOrder(Set<Excursion> excursionList, long orderId) {

    }


}
