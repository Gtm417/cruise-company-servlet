package ua.training.model.dao.implement;

import ua.training.model.dao.TicketDao;
import ua.training.model.dao.mapper.CruiseMapper;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.dto.TicketCruiseDTO;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ticket;
import ua.training.model.exception.DuplicateDataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCTicketDao implements TicketDao {
    private static final String TICKETS_PRICE_WITH_DISCOUNT = "SELECT tickets.id, ticket_name, price - price * discount/100 as price, " +
            "cruises.id, cruise_name, arrival_date, departure_date, description_eng, description_ru FROM tickets " +
            "INNER JOIN cruises ON tickets.cruise_id = cruises.id " +
            "WHERE cruise_id = ?";

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
    public List<TicketCruiseDTO> getTicketsPriceByCruiseId(long id) {
      List<TicketCruiseDTO> tickets = new ArrayList<>();
      HashMap<Long, Cruise> cruises = new HashMap<>();
        try(Connection connection = connectionPoolHolder.getConnection();
            PreparedStatement ps = connection.prepareStatement(TICKETS_PRICE_WITH_DISCOUNT)){
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ObjectMapper<Cruise> mapper = new CruiseMapper();
            while(rs.next()){
                TicketCruiseDTO ticketCruiseDTO = new TicketCruiseDTO(rs.getLong("id"),
                        rs.getString("ticket_name"),
                        rs.getLong("price"));
                Cruise cruise = mapper.extractFromResultSet(rs);
                ticketCruiseDTO.setCruise(mapper.makeUnique(cruises,cruise));
                tickets.add(ticketCruiseDTO);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return tickets;
    }
}
