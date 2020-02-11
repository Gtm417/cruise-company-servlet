package ua.training.web.mapper;

import javax.servlet.http.HttpServletRequest;


@FunctionalInterface
public interface RequestFormMapper<T> {
    T mapToForm(HttpServletRequest request);
}
