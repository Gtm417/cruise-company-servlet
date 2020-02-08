package ua.training.model.dao;

import ua.training.exception.DuplicateDataBaseException;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    boolean create(T entity) throws DuplicateDataBaseException;

    Optional<T> findById(long id);

    List<T> findAll();

    boolean update(T entity);

    void delete(int id);
}
