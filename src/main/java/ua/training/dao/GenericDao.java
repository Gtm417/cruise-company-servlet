package ua.training.dao;

import ua.training.exception.DuplicateDataBaseException;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    default boolean create(T entity) throws DuplicateDataBaseException {
        throw new UnsupportedOperationException();
    }

    default Optional<T> findById(long id) {
        throw new UnsupportedOperationException();
    }

    default List<T> findAll() {
        throw new UnsupportedOperationException();
    }

    default boolean update(T entity) {
        throw new UnsupportedOperationException();
    }

    default void delete(int id) {
        throw new UnsupportedOperationException();
    }
}
