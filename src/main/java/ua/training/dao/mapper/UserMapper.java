package ua.training.dao.mapper;

import ua.training.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.builder().id(rs.getInt("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .role(User.ROLE.valueOf(rs.getString("role")))
                .balance(rs.getLong("balance"))
                .build();
    }

    @Override
    public User makeUnique(Map<Long, User> cache, User entity) {
        return null;
    }
}
