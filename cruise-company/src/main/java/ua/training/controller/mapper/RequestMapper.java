package ua.training.controller.mapper;

import javax.servlet.http.HttpServletRequest;

public interface RequestMapper<T> {
    T mapToEntity(HttpServletRequest request);
}
