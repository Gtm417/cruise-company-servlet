package ua.training.service;

import ua.training.exception.DuplicateDataBaseException;
import ua.training.exception.TicketNotFound;
import ua.training.exception.TicketsEmptyListException;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.TicketDao;
import ua.training.model.dto.TicketCruiseDTO;
import ua.training.model.entity.Ticket;

import java.util.List;

public class TicketService {
    private final TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = DaoFactory.getInstance().createTicketDao();
    }

    public void addNewTicket(Ticket ticket) throws DuplicateDataBaseException {
        ticketDao.create(ticket);
    }

    public List<TicketCruiseDTO> showTicketsForBuy(long id) throws TicketsEmptyListException {
        List<TicketCruiseDTO> tickets = ticketDao.getTicketsPriceByCruiseId(id);
        if (tickets.isEmpty()) {
            throw new TicketsEmptyListException("There is no tickets on this cruise");
        }
        return tickets;
    }

    public Ticket findTicketById(long ticketId) throws TicketNotFound {
        return ticketDao.findById(ticketId)
                .orElseThrow(() -> new TicketNotFound("ticket not found with id: ", ticketId));
    }
}