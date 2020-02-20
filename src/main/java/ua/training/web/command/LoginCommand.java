package ua.training.web.command;

import ua.training.exception.UserNotFoundException;
import ua.training.service.UserService;
import ua.training.web.form.UserForm;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.mapper.RequestFormMapper;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.SESSION_USER_ATTR;
import static ua.training.web.CommandConstants.*;
import static ua.training.web.PageConstants.LOGIN_JSP;

public class LoginCommand extends MultipleMethodCommand {
    private final UserService userService;
    private final RequestFormMapper<UserForm> userRequestFormMapper;

    public LoginCommand(UserService userService, RequestFormMapper<UserForm> userRequestFormMapper) {
        this.userService = userService;
        this.userRequestFormMapper = userRequestFormMapper;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        return LOGIN_JSP;
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        if (request.getSession().getAttribute(SESSION_USER_ATTR) != null) {
            return REDIRECT_COMMAND + LOGOUT_COMMAND;
        }

        UserForm userForm = userRequestFormMapper.mapToForm(request);

        try {
            CommandUtility.setUserInSession(request, userService.login(userForm));
        } catch (UserNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, LOGIN_JSP);
            return exceptionHandler.handling(request);
        }
        return REDIRECT_COMMAND + MAIN_COMMAND;
    }
}
