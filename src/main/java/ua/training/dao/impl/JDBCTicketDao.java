package ua.training.dao.impl;

import ua.training.exception.DuplicateDataBaseException;
import ua.training.dao.ConnectionPoolHolder;
import ua.training.dao.TicketDao;
import ua.training.dao.mapper.CruiseMapper;
import ua.training.dao.mapper.ObjectMapper;
import ua.training.dao.mapper.TicketMapper;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCTicketDao implements TicketDao {
    private static final String FIND_ALL_BY_CRUISE_ID = "SELECT * FROM tickets WHERE cruise_id = ?";
    private static final String INSERT_TICKET = "INSERT INTO tickets(ticket_name, discount, price, discount_price ,cruise_id) VALUES (?,?,?,?,?)";
    private static final String FIND_BY_ID = "SELECT id, ticket_name, price, discount, discount_price FROM tickets WHERE id = ?";


    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCTicketDao(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(Ticket entity) throws DuplicateDataBaseException {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_TICKET)) {
            extractPrepareStatement(entity, ps);
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new DuplicateDataBaseException("Cruise already has such ticket", entity);
        } catch (SQLException e) {
            //todo status 500 exception
            e.printStackTrace();
            return false;
        }
    }

    private void extractPrepareStatement(Ticket entity, PreparedStatement ps) throws SQLException {
        ps.setString(1, entity.getTicketName());
        ps.setInt(2, entity.getDiscount());
        ps.setLong(3, entity.getPrice());
        ps.setLong(4, entity.getPriceWithDiscount());
        ps.setLong(5, entity.getCruise().getId());
        ps.executeUpdate();
    }

    @Override
    public Optional<Ticket> findById(long id) {
        ObjectMapper<Ticket> ticketMapper = new TicketMapper();
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(ticketMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            //todo DB exception
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    public boolean update(Ticket entity) {
        return false;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Ticket> findAllByCruiseId(long id) {
        List<Ticket> tickets = new ArrayList<>();
        ObjectMapper<Ticket> ticketMapper = new TicketMapper();
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BY_CRUISE_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket ticket = ticketMapper.extractFromResultSet(rs);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
