package ua.training;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.TicketDao;
import ua.training.model.exception.TicketsEmptyListException;
import ua.training.model.service.CruiseService;

public class MainTest {
    public static void main(String[] args) {
        TicketDao dao = DaoFactory.getInstance().createTicketDao();
        CruiseService service = new CruiseService();
        try {
            System.out.println(service.showTicketsForBuy(1));
        } catch (TicketsEmptyListException e) {
            e.printStackTrace();
        }
        //System.out.println(dao.getTicketsPriceByCruiseId(1));
    }
}
