package ua.training.model.dao.implement;

import ua.training.model.dao.ExcursionDao;
import ua.training.model.dao.mapper.ExcursionMapper;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.dao.mapper.PortMapper;
import ua.training.model.entity.Excursion;
import ua.training.model.entity.Port;
import ua.training.model.exception.DuplicateDataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCExcursionDao implements ExcursionDao {
    private final static String FIND_ALL_EXCURSION_FOR_CRUISE = "SELECT excursions.id,excursion_name,price,duration, ports.id, port_name FROM ports_cruises " +
            "inner join excursions ON ports_cruises.port_id = excursions.port_id " +
            "inner join ports ON ports_cruises.port_id = ports.id " +
            "WHERE cruise_id = ?";

    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCExcursionDao(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(Excursion entity) throws DuplicateDataBaseException {
        return false;
    }

    @Override
    public Excursion findById(int id) {
        return null;
    }

    @Override
    public List<Excursion> findAll() {
        return null;
    }

    @Override
    public boolean update(Excursion entity) {
        return false;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Excursion> findAllExcursionsByCruiseId(long cruiseId) {
        List<Excursion> excursions = new ArrayList<>();
        ObjectMapper<Excursion> excursionMapper = new ExcursionMapper();
        ObjectMapper<Port> portMapper = new PortMapper();
        HashMap<Long, Port> ports = new HashMap<>();
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
            e.printStackTrace();
        }
        return excursions;
    }



}
