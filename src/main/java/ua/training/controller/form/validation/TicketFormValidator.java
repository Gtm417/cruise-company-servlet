package ua.training.controller.form.validation;

import ua.training.controller.form.TicketForm;

public class TicketFormValidator implements Validator<TicketForm> {

    public static final String NUMBER_REGEX = "([1-9]{1}[0-9]{0,3})";
    public static final String TICKET_NAME_REGEX = "[A-Z].{2,10}";
    public static final long MIN_VALUE_FOR_MONEY = 0;
    public static final int MIN_VALUE_PERCENT = 0;
    public static final int MAX_VALUE_PERCENT = 100;
//    public static final String TICKET_NAME_NOT_VALID = "Required.Only upper case.From 3 to 10 symbols";
//    public static final String DISCOUNT_NOT_VALID = "Required. From 0 to 100";
//    public static final String PRICE_NOT_VALID = "Required.Min value is 0. Max value is 9999";


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
