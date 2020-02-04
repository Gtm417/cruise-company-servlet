package ua.training.model.dao.mapper;

import ua.training.model.entity.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TicketMapper implements ObjectMapper<Ticket> {
    @Override
    public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
        return Ticket.builder()
                .id(rs.getLong("tickets.id"))
                .price(rs.getLong("tickets.price"))
                .discount(rs.getInt("discount"))
                .ticketName(rs.getString("ticket_name"))
                .build();
    }

    @Override
    public Ticket makeUnique(Map<Long, Ticket> cache, Ticket entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
