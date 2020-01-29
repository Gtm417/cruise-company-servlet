package ua.training.model.dao;

import ua.training.model.dto.TicketDTO;
import ua.training.model.entity.Ticket;

import java.util.List;

public interface TicketDao extends GenericDao<Ticket> {
    List<Long> getTicketsPriceWithDiscountByCruiseId(Long id);
    List<TicketDTO> getTicketsPriceByCruiseId(long id);
}
