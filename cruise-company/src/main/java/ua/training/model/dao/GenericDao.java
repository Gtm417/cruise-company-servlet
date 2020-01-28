package ua.training.model.dao;

import ua.training.model.exception.DuplicateDataBaseException;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable{
    boolean create (T entity) throws DuplicateDataBaseException;
    T findById(int id);
    List<T> findAll();
    boolean update(T entity);
    void delete(int id);
}
