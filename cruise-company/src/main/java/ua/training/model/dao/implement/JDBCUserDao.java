package ua.training.model.dao.implement;


import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.dao.mapper.UserMapper;
import ua.training.model.entity.User;
import ua.training.model.exception.DuplicateDataBaseException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JDBCUserDao implements UserDao {
    private final static String FIND_ALL_USERS = "select * from cruise_company_servlet.users";
    private final static String FIND_USER_BY_LOGIN= "select * from cruise_company_servlet.users where login = ?";
    private final static String SAVE_USER = "insert into cruise_company_servlet.users(login, password) values(?,?)";

    private final Connection connection;

    public JDBCUserDao(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(User entity) throws DuplicateDataBaseException {
        try (PreparedStatement pst = connection.prepareStatement(SAVE_USER)){
            pst.setString(1, entity.getLogin());
            pst.setString(2, entity.getPassword());
            return  pst.executeUpdate() != 0;
        }catch(SQLException ex){
            throw new DuplicateDataBaseException("User is already exist", entity);
        }
    }

    @Override
    public User findById(int id) {
        return null;
    }

    //todo: norm exception
    @Override
    public Optional<User> findByLogin(String login) {
        try(PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_LOGIN)){
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("There is user");
                //TODo mb export in constr
                ObjectMapper<User> mapper = new UserMapper();
                return Optional.of(mapper.extractFromResultSet(rs));
            }
            System.out.println("Empty user");
            return Optional.empty();
        }catch (SQLException ex){
            //TODO: UserNotFoundException
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> resultList = new ArrayList<>();
        //TODo mb export in constr
        ObjectMapper<User> mapper = new UserMapper();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(FIND_ALL_USERS);
            while ( rs.next() ){
                resultList.add(mapper.extractFromResultSet(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }



    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close(){

    }
}
