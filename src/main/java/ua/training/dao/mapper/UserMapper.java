package ua.training.dao.mapper;

import ua.training.entity.Role;
import ua.training.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import static ua.training.dao.TableConstants.*;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.builder().id(rs.getInt(ID))
                .login(rs.getString(USERS_LOGIN_COLUMN))
                .password(rs.getString(USERS_PASSWORD_COLUMN))
                .role(Role.valueOf(rs.getString(USERS_ROLE_COLUMN)))
                .balance(rs.getLong(USERS_BALANCE_COLUMN))
                .build();
    }
}
