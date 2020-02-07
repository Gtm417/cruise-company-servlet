package ua.training.controller.mapper;

import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class UserRequestMapper implements RequestMapper<User> {

    @Override
    public User mapToEntity(HttpServletRequest request) {
        return User.builder()
                .login(request.getParameter("name"))
                .password(request.getParameter("pass"))
                .build();
    }
}
