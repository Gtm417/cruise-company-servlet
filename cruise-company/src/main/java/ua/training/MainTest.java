package ua.training;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.ExcursionDao;
import ua.training.model.dao.TicketDao;
import ua.training.model.entity.Excursion;
import ua.training.model.exception.TicketsEmptyListException;
import ua.training.model.service.CruiseService;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        ExcursionDao dao = DaoFactory.getInstance().createExcursionDao();
        List<Excursion> excursions = dao.findAllExcursionsByCruiseId(1);
        excursions.stream().map(Excursion::getExcursionName).forEach(System.out::println);
        //System.out.println(dao.getTicketsPriceByCruiseId(1));
    }
}
