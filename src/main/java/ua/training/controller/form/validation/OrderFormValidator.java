package ua.training.controller.form.validation;

import ua.training.controller.form.OrderForm;

public class OrderFormValidator implements Validator<OrderForm> {

    public static final String NAME_REGEX = "[A-Z][a-z]+";
    public static final String REGEX_NUMBER = "[1-9]{1}[0-9]*";
//    public static final String NOT_VALID_TICKET = "Not valid ticket";
//    public static final String MESSAGE_NAME = "Required.First latter have to be upper";


    @Override
    public boolean validate(OrderForm form) {
        return stringParamValidate(form.getFirstName(), NAME_REGEX)
                || stringParamValidate(form.getSecondName(), NAME_REGEX)
                || stringParamValidate(form.getTicket(), REGEX_NUMBER);
    }

}
