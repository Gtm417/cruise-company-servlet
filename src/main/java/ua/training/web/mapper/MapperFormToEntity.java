package ua.training.web.mapper;


@FunctionalInterface
public interface MapperFormToEntity<T, E> {
    T mapToEntity(E form);

}
