package ua.training;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.ExcursionDao;
import ua.training.model.dao.TicketDao;
import ua.training.model.entity.Excursion;
import ua.training.model.entity.Ticket;
import ua.training.model.exception.TicketsEmptyListException;
import ua.training.model.service.CruiseService;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        TicketDao dao = DaoFactory.getInstance().createTicketDao();
        Ticket ticket = dao.findById(10).orElseThrow(() -> new RuntimeException());
        System.out.println(ticket);
        //System.out.println(dao.getTicketsPriceByCruiseId(1));
    }
}
