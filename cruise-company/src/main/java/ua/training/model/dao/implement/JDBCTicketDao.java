package ua.training.model.dao.implement;

import ua.training.exception.DuplicateDataBaseException;
import ua.training.model.dao.ConnectionPoolHolder;
import ua.training.model.dao.TicketDao;
import ua.training.model.dao.mapper.CruiseMapper;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.dao.mapper.TicketMapper;
import ua.training.model.dto.TicketCruiseDTO;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class JDBCTicketDao implements TicketDao {
    private static final String TICKETS_PRICE_WITH_DISCOUNT = "SELECT tickets.id, ticket_name, price, discount, discount_price, " +
            "cruises.id, cruise_name, arrival_date, departure_date, description_eng, description_ru FROM tickets " +
            "INNER JOIN cruises ON tickets.cruise_id = cruises.id " +
            "WHERE cruise_id = ?";
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
        }
        catch (SQLIntegrityConstraintViolationException ex) {
            throw new DuplicateDataBaseException("Cruise already has such ticket", entity);
        }
        catch (SQLException e) {
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
    public List<Ticket> getTicketsPriceByCruiseId(long id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection connection = connectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(TICKETS_PRICE_WITH_DISCOUNT)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ObjectMapper<Cruise> cruiseMapper = new CruiseMapper();
            ObjectMapper<Ticket> ticketMapper = new TicketMapper();

            while (rs.next()) {
                Ticket ticket = ticketMapper.extractFromResultSet(rs);
                ticket.setCruise(cruiseMapper.extractFromResultSet(rs));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
