package ua.training.dao;

import ua.training.entity.Excursion;

import java.util.List;

public interface ExcursionDao extends GenericDao<Excursion> {
    List<Excursion> findAllExcursionsByCruiseId(long cruiseId);
}
