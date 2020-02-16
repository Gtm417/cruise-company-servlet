package ua.training.dao;

import ua.training.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketDao extends GenericDao<Ticket> {
    List<Ticket> findAllByCruiseId(long id);

    Optional<Ticket> findByIdAndCruiseId(long ticketId, long cruiseId);
}
