package ua.training.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.ExcursionDao;
import ua.training.model.entity.Excursion;

import java.util.List;

public class ExcursionService {
    private final ExcursionDao excursionDao;

    public ExcursionService() {
        this.excursionDao = DaoFactory.getInstance().createExcursionDao();
    }

    public List<Excursion> showAllExcursionsInCruise(long cruiseId) {
        //todo Exception if list empty
        return excursionDao.findAllExcursionsByCruiseId(cruiseId);
    }
}
