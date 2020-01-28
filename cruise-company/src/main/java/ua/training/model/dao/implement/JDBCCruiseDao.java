package ua.training.model.dao.implement;

import ua.training.model.dao.CruiseDao;
import ua.training.model.dao.mapper.CruiseMapper;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.dao.mapper.ShipMapper;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ship;
import ua.training.model.exception.DuplicateDataBaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCCruiseDao implements CruiseDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM cruises inner join ships ON cruises.id = ships.id;";
    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCCruiseDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(Cruise entity) throws DuplicateDataBaseException {
        return false;
    }

    @Override
    public Cruise findById(int id) {
        return null;
    }

    @Override
    public List<Cruise> findAll() {
        List<Cruise> resultList = new ArrayList<>();
        HashMap<Long, Ship> ships = new HashMap<>();
        ObjectMapper<Cruise> cruiseMapper = new CruiseMapper();
        ObjectMapper<Ship> shipMapper = new ShipMapper();
        try (Connection connection = connectionPoolHolder.getConnection();
                Statement pst = connection.createStatement()){
            ResultSet rs = pst.executeQuery(FIND_ALL_QUERY);
            while(rs.next()) {
                Cruise cruise = cruiseMapper.extractFromResultSet(rs);
                Ship ship = shipMapper.extractFromResultSet(rs);
                cruise.setShip(shipMapper.makeUnique(ships, ship));
                resultList.add(cruise);
            }
            return resultList;
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public boolean update(Cruise entity) {return false;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() throws Exception {
        System.out.println("CLOOOOSE");
    }
}
