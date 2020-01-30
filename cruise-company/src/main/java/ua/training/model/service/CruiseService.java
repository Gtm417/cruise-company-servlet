package ua.training.model.service;

import ua.training.model.dao.CruiseDao;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.TicketDao;
import ua.training.model.dto.TicketCruiseDTO;
import ua.training.model.entity.Cruise;
import ua.training.model.exception.TicketsEmptyListException;

import java.util.List;

public class CruiseService {
    private final CruiseDao cruiseDao;
    private final TicketDao ticketDao;

    public CruiseService()
    {
        this.cruiseDao = DaoFactory.getInstance().createCruiseDao();
        this.ticketDao = DaoFactory.getInstance().createTicketDao();
    }

    public List<Cruise> getAllCruises(){
        try{
            return cruiseDao.findAll();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    public List<TicketCruiseDTO> showTicketsForBuy(long id) throws TicketsEmptyListException {
        List<TicketCruiseDTO> tickets = ticketDao.getTicketsPriceByCruiseId(id);
        if(tickets.isEmpty()){
            throw new TicketsEmptyListException("There is no tickets on this cruise");
        }
        return tickets;
    }
}
