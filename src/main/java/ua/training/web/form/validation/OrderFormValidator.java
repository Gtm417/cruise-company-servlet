package ua.training.web.form.validation;

import ua.training.web.form.OrderForm;

public class OrderFormValidator implements Validator<OrderForm> {

    private static final String NAME_REGEX = "[A-Z][a-z]+";
    private static final String REGEX_NUMBER = "[1-9]{1}[0-9]*";


    @Override
    public boolean validate(OrderForm form) {
        return stringParamValidate(form.getFirstName(), NAME_REGEX)
                || stringParamValidate(form.getSecondName(), NAME_REGEX)
                || stringParamValidate(form.getTicket(), REGEX_NUMBER);
    }

}
