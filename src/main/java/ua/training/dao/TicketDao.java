package ua.training.dao;

import ua.training.entity.Ticket;

import java.util.List;

public interface TicketDao extends GenericDao<Ticket> {
    List<Ticket> findAllByCruiseId(long id);
}
