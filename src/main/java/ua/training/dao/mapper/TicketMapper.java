package ua.training.dao.mapper;

import ua.training.entity.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import static ua.training.dao.TableConstants.*;

public class TicketMapper implements ObjectMapper<Ticket> {
    @Override
    public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
        return Ticket.builder()
                .id(rs.getLong(TICKETS_ID_COLUMN))
                .price(rs.getLong(TICKETS_PRICE_COLUMN))
                .discount(rs.getInt(TICKETS_DISCOUNT_COLUMN))
                .priceWithDiscount(rs.getLong(TICKETS_DISCOUNT_PRICE_COLUMN))
                .ticketName(rs.getString(TICKET_NAME_COLUMN))
                .build();
    }

}
