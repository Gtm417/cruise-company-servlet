package ua.training.controller.mapper;


@FunctionalInterface
public interface MapperFormToEntity<T, E> {
    T mapToEntity(E form);

}
