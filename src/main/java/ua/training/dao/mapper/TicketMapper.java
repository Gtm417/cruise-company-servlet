package ua.training.dao.mapper;

import ua.training.entity.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper implements ObjectMapper<Ticket> {
    @Override
    public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
        return Ticket.builder()
                .id(rs.getLong("tickets.id"))
                .price(rs.getLong("tickets.price"))
                .discount(rs.getInt("discount"))
                .priceWithDiscount(rs.getLong("discount_price"))
                .ticketName(rs.getString("ticket_name"))
                .build();
    }

}
