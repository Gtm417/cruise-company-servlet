package ua.training.service;

import ua.training.dao.DaoFactory;
import ua.training.dao.TicketDao;
import ua.training.entity.Ticket;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.exception.TicketNotFound;

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

    public List<Ticket> findAllTicketsForBuy(long id) {
        return ticketDao.findAllByCruiseId(id);
    }

    public Ticket findTicketByIdAndCruise(long ticketId, long cruiseId) throws TicketNotFound {
        return ticketDao.findByIdAndCruiseId(ticketId, cruiseId)
                .orElseThrow(() -> new TicketNotFound("ticket not found with id: " + ticketId));
    }

    private long calcTicketPriceWithDiscount(Ticket ticket) {
        return ticket.getPrice() - Math.round(((double) ticket.getPrice() * ticket.getDiscount() / ONE_HUNDRED_PERCENT));
    }
}
