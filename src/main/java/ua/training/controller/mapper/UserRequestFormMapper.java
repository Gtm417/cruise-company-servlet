package ua.training.controller.mapper;

import ua.training.controller.form.UserForm;

import javax.servlet.http.HttpServletRequest;

public class UserRequestFormMapper implements RequestFormMapper<UserForm> {

    @Override
    public UserForm mapToForm(HttpServletRequest request) {
        return new UserForm(request.getParameter("name"), request.getParameter("pass"));
    }
}
