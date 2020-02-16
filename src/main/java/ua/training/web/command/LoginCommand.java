package ua.training.web.command;

import ua.training.exception.UserNotFoundException;
import ua.training.service.UserService;
import ua.training.web.form.UserForm;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.mapper.RequestFormMapper;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand extends MultipleMethodCommand {
    private final UserService userService;
    private final RequestFormMapper<UserForm> userRequestFormMapper;

    public LoginCommand(UserService userService, RequestFormMapper<UserForm> userRequestFormMapper) {
        this.userService = userService;
        this.userRequestFormMapper = userRequestFormMapper;
    }

    @Override
    protected String performGet(HttpServletRequest request) {

        return "login.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            return "redirect:logout";
        }

        UserForm userForm = userRequestFormMapper.mapToForm(request);

        try {
            CommandUtility.setUserInSession(request, userService.login(userForm));
        } catch (UserNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "login.jsp");
            return exceptionHandler.handling(request);
        }
        return "redirect:main";
    }
}
