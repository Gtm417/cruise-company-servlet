package ua.training.controller.command.validation;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class OrderRequestValidator extends RequestParameterValidator {


    public static final String NAME_REGEX = "[A-Z][a-z]+";
    public static final String MESSAGE_NAME = "Required.First latter have to be upper";
    public static final String REGEX_NUMBER = "[0-9]";
    public static final String NOT_VALID_TICKET = "Not valid ticket";

    @Override
    public List<String> validate(HttpServletRequest request) {
        setValidationMessages(new ArrayList<>());
        validStringParam(request.getParameter("firstName"),NAME_REGEX, MESSAGE_NAME);
        validStringParam(request.getParameter("secondName"),NAME_REGEX,MESSAGE_NAME);
        validStringParam(request.getParameter("ticket"), REGEX_NUMBER, NOT_VALID_TICKET);
        return getValidationMessages();
    }
}
