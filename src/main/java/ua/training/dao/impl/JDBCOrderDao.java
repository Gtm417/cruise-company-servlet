package ua.training.dao.impl;

import ua.training.dao.OrderDao;
import ua.training.dao.mapper.CruiseMapper;
import ua.training.dao.mapper.ObjectMapper;
import ua.training.dao.mapper.OrderMapper;
import ua.training.dao.mapper.TicketMapper;
import ua.training.entity.*;
import ua.training.exception.DBConnectionException;
import ua.training.exception.SaveOrderException;
import ua.training.persistance.ConnectionPoolHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCOrderDao implements OrderDao {

    public static final String INSERT_INTO_ORDER_EXCURSION = "INSERT INTO order_excursions(order_id, excursion_id) VALUES (?,?);";
    public static final String UPDATE_SHIP_BEFORE_ORDER = "UPDATE ships SET current_amount_of_passenger = ? WHERE id = ?;";
    public static final String COUNT_QUERY = "SELECT COUNT(*) as count FROM orders WHERE user_id = ?";
    private final static String CREATE_NEW_ORDER = "INSERT INTO orders(cruise_id, user_id, ticket_id,first_name, second_name, price) values(?,?,?,?,?,?);";
    private final static String UPDATE_USER = "UPDATE cruise_company_servlet.users SET  login = ?, password = ?, balance = ?, role = ?" +
            "WHERE id = ?";
    private static final String FIND_ALL_BY_CRUISE_ID = "SELECT * from orders " +
            "INNER JOIN tickets ON orders.ticket_id = tickets.id " +
            "WHERE orders.cruise_id = ?;";
    private static final String FIND_ALL_BY_USER_ID = "SELECT * from orders " +
            "INNER JOIN tickets ON orders.ticket_id = tickets.id " +
            "INNER JOIN cruises ON orders.cruise_id = cruises.id " +
            "WHERE orders.user_id = ? LIMIT ?,?;";
    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCOrderDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }


    @Override
    public void buyCruiseChanges(User user, Order order) throws SaveOrderException {
        try (Connection connection = connectionPoolHolder.getConnection()) {
            try (PreparedStatement psUser = connection.prepareStatement(UPDATE_USER);
                 PreparedStatement psOrder = connection.prepareStatement(CREATE_NEW_ORDER,
                         Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psExcursion = connection.prepareStatement(INSERT_INTO_ORDER_EXCURSION);
                 PreparedStatement psShip = connection.prepareStatement(UPDATE_SHIP_BEFORE_ORDER)) {
                connection.setAutoCommit(false);

                fillShipUpdatePrepareStatement(order.getCruise(), psShip);
                fillUserUpdatePrepareStatement(user, psUser);
                fillOrderInsertPrepareStatement(order, psOrder);
                long orderId = getOrderInsertGeneratedKey(psOrder);
                for (Excursion exc : order.getExcursionList()) {
                    fillExcursionInsertPrepareStatement(exc, orderId, psExcursion);
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SaveOrderException("Cannot save order to database");
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }

    private long getOrderInsertGeneratedKey(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getLong(1);
        }
        throw new SQLException();
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

    private void fillExcursionInsertPrepareStatement(Excursion excursion, long orderId, PreparedStatement psUser) throws SQLException {
        psUser.setLong(1, orderId);
        psUser.setLong(2, excursion.getId());
        psUser.executeUpdate();
    }

    private void fillShipUpdatePrepareStatement(Cruise cruise, PreparedStatement psUser) throws SQLException {
        psUser.setLong(1, cruise.getShip().getCurrentPassengerAmount() + 1);
        psUser.setLong(2, cruise.getShip().getId());
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
            throw new DBConnectionException(e);
        }
        return orders;
    }

    @Override
    public List<Order> findAllOrdersByUserWithOffsetAndLimit(int offset, int limit, long id) {
        List<Order> orders = new ArrayList<>();
        ObjectMapper<Order> orderMapper = new OrderMapper();
        ObjectMapper<Ticket> ticketMapper = new TicketMapper();
        ObjectMapper<Cruise> cruiseMapper = new CruiseMapper();
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BY_USER_ID)) {
            ps.setLong(1, id);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = orderMapper.extractFromResultSet(rs);
                order.setTicket(ticketMapper.extractFromResultSet(rs));
                order.setCruise(cruiseMapper.extractFromResultSet(rs));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return orders;
    }

    @Override
    public long countOrdersByUserId(long id) {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(COUNT_QUERY)) {
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            //LOGGER.error("Exception during counting entities", e);
            throw new DBConnectionException("Exception during counting orders", e);
        }
    }
}
