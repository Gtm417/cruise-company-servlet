package ua.training.dao;

import ua.training.entity.Excursion;

import java.util.List;
import java.util.Set;

public interface ExcursionDao extends GenericDao<Excursion> {
    List<Excursion> findAllExcursionsByCruiseId(long cruiseId);
}
