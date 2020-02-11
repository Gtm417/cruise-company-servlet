package ua.training.service;

import ua.training.exception.ExcursionNotFound;
import ua.training.dao.DaoFactory;
import ua.training.dao.ExcursionDao;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Excursion;

import java.util.List;

public class ExcursionService {
    private final ExcursionDao excursionDao;

    public ExcursionService() {
        this.excursionDao = DaoFactory.getInstance().createExcursionDao();
    }

    public List<Excursion> showAllExcursionsInCruise(Cruise cruise) {
        //todo Exception if list empty
        return excursionDao.findAllExcursionsByCruiseId(cruise.getId());
    }

    public Excursion findById(long id) throws ExcursionNotFound {
        return excursionDao.findById(id)
                .orElseThrow(() -> new ExcursionNotFound("Excursion Not Found with id: ", id));
    }
}
