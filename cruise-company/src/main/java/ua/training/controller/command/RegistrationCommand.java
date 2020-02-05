package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.model.entity.User;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private final UserService userService;
    private final RequestMapper<User> userRequestMapper;


    public RegistrationCommand(UserService userService, RequestMapper<User> userRequestMapper) {
        this.userService = userService;
        this.userRequestMapper = userRequestMapper;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("name");
        String pass = request.getParameter("pass");

        //todo valid regex
        //todo default null valid
        if (login == null || login.equals("") || pass == null || pass.equals("")) {
            return "/registration.jsp";
        }

        try {
            userService.saveNewUser(userRequestMapper.mapToEntity(request));
        } catch (DuplicateDataBaseException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "registration.jsp");
            return exceptionHandler.handling(request);
        }
        request.getSession().setAttribute("success", true);
        return "redirect:login";
    }
}
