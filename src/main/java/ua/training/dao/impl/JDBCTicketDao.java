package ua.training.dao.impl;

import ua.training.dao.TicketDao;
import ua.training.dao.mapper.ObjectMapper;
import ua.training.dao.mapper.TicketMapper;
import ua.training.entity.Ticket;
import ua.training.exception.DBConnectionException;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.persistance.ConnectionPoolHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.training.dao.TableConstants.*;

public class JDBCTicketDao implements TicketDao {
    private static final String FIND_ALL_BY_CRUISE_ID = "SELECT * FROM " + TICKETS_TABLE + " WHERE " + TICKETS_CRUISE_ID + " = ?";

    private static final String INSERT_TICKET = "INSERT INTO " + TICKETS_TABLE + "(" + TICKET_NAME_COLUMN + ", " + TICKETS_DISCOUNT_COLUMN + ", " +
            PRICE_COLUMN + ", " + TICKETS_DISCOUNT_PRICE_COLUMN + ", " + TICKETS_CRUISE_ID +
            ") VALUES (?,?,?,?,?)";

    private static final String FIND_BY_ID_AND_CRUISE = "SELECT * FROM " + TICKETS_TABLE + " WHERE " + ID + " = ? AND " + TICKETS_CRUISE_ID + " = ?";


    private final ConnectionPoolHolder connectionPoolHolder;
    private ObjectMapper<Ticket> ticketMapper;

    public JDBCTicketDao(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
        this.ticketMapper = new TicketMapper();
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
            throw new DBConnectionException(e);
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
    public Optional<Ticket> findByIdAndCruiseId(long id, long cruiseId) {
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_AND_CRUISE)) {
            ps.setLong(1, id);
            ps.setLong(2, cruiseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(ticketMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Ticket> findAllByCruiseId(long id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_BY_CRUISE_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket ticket = ticketMapper.extractFromResultSet(rs);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return tickets;
    }
}
