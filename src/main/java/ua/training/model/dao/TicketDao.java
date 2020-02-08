package ua.training.model.dao;

import ua.training.model.dto.TicketCruiseDTO;
import ua.training.model.entity.Ticket;

import java.util.List;

public interface TicketDao extends GenericDao<Ticket> {
    List<Ticket> getTicketsPriceByCruiseId(long id);
}