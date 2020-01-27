package ua.training.model.dao.implement;


import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;

import java.sql.Connection;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public UserDao createUserDao(Connection connection) {
        return new JDBCUserDao(connection);
    }

}
