package ua.training.model.dao.implement;


import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JDBCUserDao implements UserDao {
    private final static String FIND_ALL_USERS = "select * from user_test.users";
    private final static String FIND_USER_BY_LOGIN= "select * from user_test.users where login = ?";

    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public Optional<User> findByLogin(String login) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_LOGIN)){
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(extractFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    static User extractFromResultSet(ResultSet rs)
            throws SQLException {
        User result = new User();

        result.setId(rs.getInt("id") );
        result.setLogin(rs.getString("login"));
        result.setPassword(rs.getString("password"));
        result.setRole(User.ROLE.valueOf(rs.getString("role")));


        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> resultList = new ArrayList<>();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(FIND_ALL_USERS);
            while ( rs.next() ){
                User user = extractFromResultSet(rs);
                resultList.add(user);
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
    public void close() {

    }
}
