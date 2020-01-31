package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.ExcursionDao;
import ua.training.model.dao.TicketDao;
import ua.training.model.dto.TicketCruiseDTO;
import ua.training.model.entity.Ticket;
import ua.training.model.exception.DuplicateDataBaseException;
import ua.training.model.exception.TicketsEmptyListException;

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
        if(tickets.isEmpty()){
            throw new TicketsEmptyListException("There is no tickets on this cruise");
        }
        return tickets;
    }

}
