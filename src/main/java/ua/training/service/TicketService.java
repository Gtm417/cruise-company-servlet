package ua.training.service;

import ua.training.dao.DaoFactory;
import ua.training.dao.TicketDao;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.exception.TicketNotFound;
import ua.training.exception.TicketsEmptyListException;
import ua.training.model.entity.Ticket;

import java.util.List;

public class TicketService {
    private static final int ONE_HUNDRED_PERCENT = 100;

    private final TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = DaoFactory.getInstance().createTicketDao();
    }

    public void addNewTicket(Ticket ticket) throws DuplicateDataBaseException {
        ticket.setPriceWithDiscount(calcTicketPriceWithDiscount(ticket));
        ticketDao.create(ticket);
    }

    public List<Ticket> showTicketsForBuy(long id) throws TicketsEmptyListException {
        List<Ticket> tickets = ticketDao.findAllByCruiseId(id);
        if (tickets.isEmpty()) {
            throw new TicketsEmptyListException("There is no tickets on this cruise");
        }
        return tickets;
    }

    public Ticket findTicketById(long ticketId) throws TicketNotFound {
        return ticketDao.findById(ticketId)
                .orElseThrow(() -> new TicketNotFound("ticket not found with id: ", ticketId));
    }

    private long calcTicketPriceWithDiscount(Ticket ticket) {
        return ticket.getPrice() - Math.round(((double) ticket.getPrice() * ticket.getDiscount() / ONE_HUNDRED_PERCENT));
    }
}
