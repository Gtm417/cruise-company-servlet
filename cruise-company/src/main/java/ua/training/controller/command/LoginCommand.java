package ua.training.controller.command;

import ua.training.controller.handler.ExceptionHandler;
import ua.training.controller.validation.RequestParameterValidator;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.UserNotFoundException;
import ua.training.model.entity.User;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private final UserService userService;
    private final RequestMapper<User> userRequestMapper;
    private final RequestParameterValidator userValidator;

    public LoginCommand(UserService userService, RequestMapper<User> userRequestMapper, RequestParameterValidator userValidator) {
        this.userService = userService;
        this.userRequestMapper = userRequestMapper;
        this.userValidator = userValidator;
    }

    @Override
    public String execute(HttpServletRequest request) {
        //todo new method
        if (request.getSession().getAttribute("user") != null) {
            return "redirect:logout";
        }

        if(!userValidator.validate(request).isEmpty()){
            request.setAttribute("errors", userValidator.getValidationMessages());
            return "/login.jsp";
        }

        User userFromRequest = userRequestMapper.mapToEntity(request);

        User user;
        try {
            user = userService.findUserByLogin(userFromRequest.getLogin());
        } catch (UserNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "login.jsp");
            return exceptionHandler.handling(request);
        }

        if (CommandUtility.checkUserIsLogged(request, user.getLogin())) {
            return "WEB-INF/error.jsp";
        }

        if (userService.checkInputPassword(userFromRequest.getPassword(), user.getPassword())) {

            CommandUtility.setUserInSession(request, user);
            return "redirect:main";
        } else {
            request.getSession().setAttribute("exception", true);
            return "/login.jsp";
        }


    }



}
