package ua.training.model.dao.implement;

import ua.training.exception.DuplicateDataBaseException;
import ua.training.model.dao.ConnectionPoolHolder;
import ua.training.model.dao.OrderDao;
import ua.training.model.dao.mapper.CruiseMapper;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.dao.mapper.OrderMapper;
import ua.training.model.dao.mapper.TicketMapper;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.model.entity.Ticket;
import ua.training.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDao implements OrderDao {
    private final static String CREATE_NEW_ORDER = "INSERT INTO orders(cruise_id, user_id, ticket_id,first_name, second_name, price) values(?,?,?,?,?,?);";
    private final static String UPDATE_USER = "UPDATE cruise_company_servlet.users SET  login = ?, password = ?, balance = ?, role = ?" +
            "WHERE id = ?";
    private static final String FIND_ALL_BY_CRUISE_ID = "SELECT * from orders " +
            "INNER JOIN tickets ON orders.ticket_id = tickets.id " +
            "WHERE orders.cruise_id = ?;";

    private static final String FIND_ALL_BY_USER_ID = "SELECT * from orders " +
            "INNER JOIN tickets ON orders.ticket_id = tickets.id " +
            "INNER JOIN cruises ON orders.cruise_id = cruises.id " +
            "WHERE orders.user_id = ?;";

    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCOrderDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(Order entity) throws DuplicateDataBaseException {
        return false;
    }

    @Override
    public Optional<Order> findById(long id) {
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
    public void buyCruiseChanges(User user, Order order) {
        try (Connection connection = connectionPoolHolder.getConnection()) {
            try (PreparedStatement psUser = connection.prepareStatement(UPDATE_USER);
                 PreparedStatement psOrder = connection.prepareStatement(CREATE_NEW_ORDER)) {
                connection.setAutoCommit(false);

                fillUserUpdatePrepareStatement(user, psUser);
                fillOrderInsertPrepareStatement(order, psOrder);
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

    private void fillOrderInsertPrepareStatement(Order order, PreparedStatement psOrder) throws SQLException {
        psOrder.setLong(1, order.getCruise().getId());
        psOrder.setLong(2, order.getUser().getId());
        psOrder.setLong(3, order.getTicket().getId());
        psOrder.setString(4, order.getFirstName());
        psOrder.setString(5, order.getSecondName());
        psOrder.setLong(6, order.getOrderPrice());
        psOrder.executeUpdate();
    }

    private void fillUserUpdatePrepareStatement(User user, PreparedStatement psUser) throws SQLException {
        psUser.setString(1, user.getLogin());
        psUser.setString(2, user.getPassword());
        psUser.setLong(3, user.getBalance());
        psUser.setString(4, user.getRole().name());
        psUser.setLong(5, user.getId());
        psUser.executeUpdate();
    }

    @Override
    public List<Order> findAllOrdersByCruise(long id) {
        List<Order> orders = new ArrayList<>();
        ObjectMapper<Order> orderMapper = new OrderMapper();
        ObjectMapper<Ticket> ticketMapper = new TicketMapper();

        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BY_CRUISE_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = orderMapper.extractFromResultSet(rs);
                order.setTicket(ticketMapper.extractFromResultSet(rs));
                orders.add(order);
            }
        } catch (SQLException e) {
            //todo db exception
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> findAllOrdersByUser(long id) {
        List<Order> orders = new ArrayList<>();
        ObjectMapper<Order> orderMapper = new OrderMapper();
        ObjectMapper<Ticket> ticketMapper = new TicketMapper();
        ObjectMapper<Cruise> cruiseMapper = new CruiseMapper();

        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BY_USER_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = orderMapper.extractFromResultSet(rs);
                order.setTicket(ticketMapper.extractFromResultSet(rs));
                order.setCruise(cruiseMapper.extractFromResultSet(rs));
                orders.add(order);
            }
        } catch (SQLException e) {
            //todo db exception
            e.printStackTrace();
        }
        return orders;
    }
}
