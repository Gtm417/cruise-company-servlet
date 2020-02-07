package ua.training.controller.validation;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserRequestParameterValidator extends RequestParameterValidator {

    public static final String LOGIN_REGEX = "^[A-Za-z0-9_-]{3,16}$";
    public static final String PASSWORD_REGEX = "^[A-Za-z0-9_-]{5,18}$";

    public static final String WRONG_NAME_MESSAGE = "Login should contains latin letters and numbers. Min values of characters 3. Max values of characters 16";
    public static final String WRONG_PASSWORD_MESSAGE = "Password should contains latin letters and numbers. Min values of characters 6. Max values of characters 18";

    @Override
    public List<String> validate(HttpServletRequest request) {
        setValidationMessages(new ArrayList<>());
        validStringParam(request.getParameter("name"), LOGIN_REGEX, WRONG_NAME_MESSAGE);
        validStringParam(request.getParameter("pass"), PASSWORD_REGEX, WRONG_PASSWORD_MESSAGE);
        return getValidationMessages();
    }

}
