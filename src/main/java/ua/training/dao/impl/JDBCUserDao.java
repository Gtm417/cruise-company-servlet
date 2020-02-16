package ua.training.dao.impl;


import ua.training.dao.UserDao;
import ua.training.dao.mapper.ObjectMapper;
import ua.training.dao.mapper.UserMapper;
import ua.training.entity.User;
import ua.training.exception.DBConnectionException;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.persistance.ConnectionPoolHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JDBCUserDao implements UserDao {
    private final static String FIND_ALL_USERS = "select * from cruise_company_servlet.users";
    private final static String FIND_USER_BY_LOGIN = "select * from cruise_company_servlet.users where login = ?";
    private final static String SAVE_USER = "insert into cruise_company_servlet.users(login, password) values(?,?)";
    private final static String UPDATE_USER = "UPDATE cruise_company_servlet.users SET login = ?, password = ?, balance = ?, role = ?" +
            "WHERE id = ?";

    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCUserDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(User entity) throws DuplicateDataBaseException {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement pst = connection.prepareStatement(SAVE_USER)) {
            pst.setString(1, entity.getLogin());
            pst.setString(2, entity.getPassword());
            return pst.executeUpdate() != 0;
        } catch (SQLException ex) {
            throw new DuplicateDataBaseException("User is already exist", entity);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ObjectMapper<User> mapper = new UserMapper();
                return Optional.of(mapper.extractFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DBConnectionException(ex);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> resultList = new ArrayList<>();
        ObjectMapper<User> mapper = new UserMapper();
        try (Connection connection = connectionPoolHolder.getConnection();
             Statement ps = connection.createStatement()) {
            ResultSet rs = ps.executeQuery(FIND_ALL_USERS);
            while (rs.next()) {
                resultList.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return resultList;
    }

    @Override
    public boolean update(User entity) {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement pst = connection.prepareStatement(UPDATE_USER)) {
            pst.setString(1, entity.getLogin());
            pst.setString(2, entity.getPassword());
            pst.setLong(3, entity.getBalance());
            pst.setString(4, entity.getRole().name());
            pst.setLong(5, entity.getId());
            return pst.executeUpdate() != 0;
        } catch (SQLException ex) {
            throw new DBConnectionException(ex);
        }
    }


}