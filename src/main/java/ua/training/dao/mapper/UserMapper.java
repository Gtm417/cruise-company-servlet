package ua.training.dao.mapper;

import ua.training.entity.Role;
import ua.training.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.builder().id(rs.getInt("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .role(Role.valueOf(rs.getString("role")))
                .balance(rs.getLong("balance"))
                .build();
    }
}
