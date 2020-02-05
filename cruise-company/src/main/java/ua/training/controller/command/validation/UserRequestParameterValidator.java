package ua.training.controller.command.validation;

import sun.java2d.pipe.SpanClipRenderer;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRequestParameterValidator implements RequestParameterValidator<User> {

    public static final String LOGIN_REGEX = "^[a-z0-9_-]{3,16}$";
    public static final String PASSWORD_REGEX = "^[a-z0-9_-]{6,18}$";

    public static final String WRONG_NAME_MESSAGE = "Login should contains latin letters and numbers. Min values of characters 3. Max values of characters 16";
    public static final String WRONG_PASSWORD_MESSAGE = "Password should contains latin letters and numbers. Min values of characters 6. Max values of characters 18";


    private List<String> validationMessages;

    @Override
    public List<String> validate(HttpServletRequest request) {
        System.out.println("validation");
        validationMessages = new ArrayList<>();
        validParam(request.getParameter("name"), LOGIN_REGEX, WRONG_NAME_MESSAGE);
        validParam(request.getParameter("pass"), PASSWORD_REGEX, WRONG_PASSWORD_MESSAGE);
        return validationMessages;
    }

    @Override
    public List<String> getValidationMessages() {
        return validationMessages;
    }

    private void validParam(String param, String regex, String message) {
        if (Objects.isNull(param) || !param.matches(regex)) {
            validationMessages.add(message);
        }
    }

}
