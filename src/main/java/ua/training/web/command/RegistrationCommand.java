package ua.training.web.command;

import ua.training.entity.User;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.service.UserService;
import ua.training.web.form.UserForm;
import ua.training.web.form.validation.Validator;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.mapper.MapperFormToEntity;
import ua.training.web.mapper.RequestFormMapper;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.*;
import static ua.training.web.CommandConstants.*;
import static ua.training.web.PageConstants.REGISTRATION_JSP;

public class RegistrationCommand extends MultipleMethodCommand {
    private final UserService userService;
    private final RequestFormMapper<UserForm> userRequestFormMapper;
    private final Validator<UserForm> userValidator;


    public RegistrationCommand(UserService userService, RequestFormMapper<UserForm> userRequestFormMapper, Validator<UserForm> userValidator) {
        this.userService = userService;
        this.userRequestFormMapper = userRequestFormMapper;
        this.userValidator = userValidator;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        if (request.getSession().getAttribute(SESSION_USER_ATTR) != null) {
            return REDIRECT_COMMAND + LOGOUT_COMMAND;
        }
        return REGISTRATION_JSP;
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        UserForm userForm = userRequestFormMapper.mapToForm(request);

        if (userValidator.validate(userForm)) {
            request.setAttribute(ERRORS_REQUEST_ATTR, true);
            return REGISTRATION_JSP;
        }

        try {
            userService.saveNewUser(getFormEntityMapper().mapToEntity(userForm));
        } catch (DuplicateDataBaseException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, REGISTRATION_JSP);
            return exceptionHandler.handling(request);
        }
        request.getSession().setAttribute(SUCCESS_SESSION_ATTR, true);
        return REDIRECT_COMMAND + LOGIN_COMMAND;
    }

    private MapperFormToEntity<User, UserForm> getFormEntityMapper() {
        return form -> User.builder()
                .login(form.getLogin())
                .password(form.getPassword())
                .build();
    }
}