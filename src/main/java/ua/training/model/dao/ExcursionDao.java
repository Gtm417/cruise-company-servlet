package ua.training.model.dao;

import ua.training.model.entity.Excursion;

import java.util.List;

public interface ExcursionDao extends GenericDao<Excursion> {
    List<Excursion> findAllExcursionsByCruiseId(long cruiseId);
}
