package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.controller.command.validation.RequestParameterValidator;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.model.entity.User;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private final UserService userService;
    private final RequestMapper<User> userRequestMapper;
    private final RequestParameterValidator userValidator;


    public RegistrationCommand(UserService userService, RequestMapper<User> userRequestMapper, RequestParameterValidator userValidator) {
        this.userService = userService;
        this.userRequestMapper = userRequestMapper;
        this.userValidator = userValidator;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (!userValidator.validate(request).isEmpty()) {
            request.setAttribute("errors", userValidator.getValidationMessages());
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
