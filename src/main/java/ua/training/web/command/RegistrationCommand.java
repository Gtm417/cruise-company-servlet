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
        return "registration.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        UserForm userForm = userRequestFormMapper.mapToForm(request);

        if (userValidator.validate(userForm)) {
            request.setAttribute("errors", true);
            return "registration.jsp";
        }

        try {
            userService.saveNewUser(getFormEntityMapper().mapToEntity(userForm));
        } catch (DuplicateDataBaseException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "registration.jsp");
            return exceptionHandler.handling(request);
        }
        request.getSession().setAttribute("success", true);
        return "redirect:login";
    }

    private MapperFormToEntity<User, UserForm> getFormEntityMapper() {
        return form -> User.builder()
                .login(form.getLogin())
                .password(form.getPassword())
                .build();
    }
}
