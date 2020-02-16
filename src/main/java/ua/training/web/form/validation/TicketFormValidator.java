package ua.training.web.form.validation;

import ua.training.web.form.TicketForm;

public class TicketFormValidator implements Validator<TicketForm> {

    public static final String NUMBER_REGEX = "([1-9][0-9]{0,3})";
    public static final String TICKET_NAME_REGEX = "[A-Z].{2,10}";
    public static final long MIN_VALUE_FOR_MONEY = 0;
    public static final int MIN_VALUE_PERCENT = 0;
    public static final int MAX_VALUE_PERCENT = 100;


    @Override
    public boolean validate(TicketForm form) {
        if (stringParamValidate(form.getName(), TICKET_NAME_REGEX)
                || stringParamValidate(form.getDiscount(), NUMBER_REGEX)
                || stringParamValidate(form.getPrice(), NUMBER_REGEX)) {
            return false;
        }
        return numberParamValid(Long.parseLong(form.getPrice()), MIN_VALUE_FOR_MONEY)
                && numberParamValid(Integer.parseInt(form.getDiscount()), MIN_VALUE_PERCENT, MAX_VALUE_PERCENT);
    }

    public boolean numberParamValid(long param, long minValue) {
        return param >= minValue;

    }
}
