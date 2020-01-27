package ua.training.model.dao.implement;

import ua.training.model.dao.CruiseDao;
import ua.training.model.dao.mapper.CruiseMapper;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.User;
import ua.training.model.exception.DuplicateDataBaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCruiseDao implements CruiseDao {
    private static final String FIND_ALL_QUERY = "SELECT * FROM cruises";
    private final Connection connection;

    public JDBCCruiseDao(final Connection connection) {
        this.connection = connection;
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
        ObjectMapper<Cruise> mapper = new CruiseMapper();
        try (Statement pst = connection.createStatement()){
            ResultSet rs = pst.executeQuery(FIND_ALL_QUERY);
            while(rs.next()) {
                resultList.add(mapper.extractFromResultSet(rs));
            }
            return resultList;
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Cruise entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() throws Exception {

    }
}
