package ua.training.dao;

import ua.training.model.entity.Ticket;

import java.util.List;

public interface TicketDao extends GenericDao<Ticket> {
    List<Ticket> findAllByCruiseId(long id);
}
