package ua.training.model.dao.implement;

import ua.training.model.dao.OrderDao;
import ua.training.model.dto.OrderDTO;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.exception.DuplicateDataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCOrderDao implements OrderDao {
    private final static String CREATE_NEW_ORDER = "INSERT INTO orders(cruise_id, user_id, ticket_id,first_name, second_name) values(?,?,?,?,?);";
    private final static String UPDATE_USER = "UPDATE cruise_company_servlet.users SET id = ?, login = ?, password = ?, balance = ?, role = ?" +
            "WHERE id = ?";

    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCOrderDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(Order entity) throws DuplicateDataBaseException {
        return false;
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean update(Order entity) {
        return false;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void buyCruiseChanges(User user, OrderDTO orderDTO) {
        try (Connection connection = connectionPoolHolder.getConnection();) {
            try (PreparedStatement psUser = connection.prepareStatement(UPDATE_USER);
                 PreparedStatement psOrder = connection.prepareStatement(CREATE_NEW_ORDER)) {
                connection.setAutoCommit(false);
                psUser.setLong(1, user.getId());
                psUser.setString(2, user.getLogin());
                psUser.setString(3, user.getPassword());
                psUser.setLong(4, user.getBalance());
                psUser.setString(5, user.getRole().name());
                psUser.setLong(6, user.getId());
                psUser.executeUpdate();
                psOrder.setLong(1, orderDTO.getCruiseId());
                psOrder.setLong(2, orderDTO.getUserId());
                psOrder.setLong(3, orderDTO.getTicketId());
                psOrder.setString(4, orderDTO.getFirstName());
                psOrder.setString(5, orderDTO.getSecondName());
                psOrder.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                connection.rollback();
                ex.printStackTrace();
                //todo throw Exception Юзер не знает что не добавился ордер
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo throw exception
        }
    }
}
