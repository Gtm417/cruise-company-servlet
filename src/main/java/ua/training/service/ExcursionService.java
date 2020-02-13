package ua.training.service;

import ua.training.dao.DaoFactory;
import ua.training.dao.ExcursionDao;
import ua.training.exception.ExcursionNotFound;
import ua.training.entity.Cruise;
import ua.training.entity.Excursion;

import java.util.List;
import java.util.Set;

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

    public long getTotalSumExcursionSet(Set<Excursion> excursions) {
        return excursions.stream().mapToLong(Excursion::getPrice).sum();
    }
}
