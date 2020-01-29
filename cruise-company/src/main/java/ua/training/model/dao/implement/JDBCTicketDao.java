package ua.training.model.dao.implement;

import ua.training.model.dao.TicketDao;
import ua.training.model.dto.TicketDTO;
import ua.training.model.entity.Ticket;
import ua.training.model.exception.DuplicateDataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCTicketDao implements TicketDao {
    private static final String TICKETS_PRICE_WITH_DISCOUNT = "SELECT id, ticket_name, price - price * discount/100 as price FROM tickets WHERE cruise_id = ?";

    private final ConnectionPoolHolder connectionPoolHolder;
    public JDBCTicketDao(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public boolean create(Ticket entity) throws DuplicateDataBaseException {
        return false;
    }

    @Override
    public Ticket findById(int id) {
        return null;
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
    public List<Long> getTicketsPriceWithDiscountByCruiseId(Long id) {
        return null;
    }

    @Override
    public List<TicketDTO> getTicketsPriceByCruiseId(long id) {
      List<TicketDTO> tickets = new ArrayList<>();
        try(Connection connection = connectionPoolHolder.getConnection();
            PreparedStatement ps = connection.prepareStatement(TICKETS_PRICE_WITH_DISCOUNT)){
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                TicketDTO ticketDTO = new TicketDTO(rs.getLong("id"),
                        rs.getString("ticket_name"),
                        rs.getLong("price"));
                tickets.add(ticketDTO);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return tickets;
    }
}
