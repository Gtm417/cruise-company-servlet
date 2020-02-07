package ua.training.controller.validation;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CruiseDescriptionValidator extends RequestParameterValidator {

    public static final String REGEX_ANY_SYMBOL = ".{1,}";
    public static final String REQUIRED = "Required";

    @Override
    public List<String> validate(HttpServletRequest request) {
        setValidationMessages(new ArrayList<>());
        validStringParam(request.getParameter("descriptionRu"), REGEX_ANY_SYMBOL, REQUIRED);
        validStringParam(request.getParameter("descriptionEng"),REGEX_ANY_SYMBOL, REQUIRED);
        return getValidationMessages();
    }
}
