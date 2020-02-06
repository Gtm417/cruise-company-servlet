package ua.training.controller.validation;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class BalanceRequestValidator extends RequestParameterValidator {
    public static final String MESSAGE_BALANCE = "Required.More 0, less 10000";
    private static final long MIN_VALUE = 0;
    private static final long MAX_VALUE = 9999;

    @Override
    public List<String> validate(HttpServletRequest request) {
        setValidationMessages(new ArrayList<>());
        String balance = request.getParameter("balance");
        try{
            validateNumberParam(Long.parseLong(balance), MIN_VALUE, MAX_VALUE);
        }catch (NumberFormatException ex) {
            getValidationMessages().add(MESSAGE_BALANCE);
        }
        return getValidationMessages();
    }

    private void validateNumberParam(long value, long minValue, long maxValue) {
        if (value < 0 || value >= 10000){
            getValidationMessages().add(MESSAGE_BALANCE);
        }
    }
}
