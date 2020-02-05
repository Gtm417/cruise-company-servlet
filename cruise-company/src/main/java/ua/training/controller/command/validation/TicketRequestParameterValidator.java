package ua.training.controller.command.validation;

import ua.training.model.entity.Ticket;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class TicketRequestParameterValidator extends RequestParameterValidator {
    public static final String NUMBER_REGEX = "[0-9].{0,4}";
    public static final String TICKET_NAME_REGEX = "[A-Z].{2,10}";
    public static final long MIN_VALUE_FOR_MONEY = 0;
    public static final int MIN_VALUE_PERCENT = 0;
    public static final int MAX_VALUE_PERCENT = 100;
    public static final String TICKET_NAME_NOT_VALID = "Required.Only upper case.From 3 to 10 symbols";
    public static final String DISCOUNT_NOT_VALID = "Required. From 0 to 100";
    public static final String PRICE_NOT_VALID = "Required.Min value is 0. Max value is 9999";


    @Override
    public List<String> validate(HttpServletRequest request) {
        setValidationMessages(new ArrayList<>());
        if(validateStringValues(request).isEmpty()){
            validNumberValues(request);
        }
        return getValidationMessages();
    }



    private List<String> validateStringValues(HttpServletRequest request){
        validStringParam(request.getParameter("ticketName"), TICKET_NAME_REGEX , TICKET_NAME_NOT_VALID);
        validStringParam(request.getParameter("price"), NUMBER_REGEX, PRICE_NOT_VALID);
        validStringParam(request.getParameter("discount"), NUMBER_REGEX ,DISCOUNT_NOT_VALID);
        return getValidationMessages();
    }

    private void validNumberValues(HttpServletRequest request) {
        validLongParam(Long.parseLong(request.getParameter("price")), MIN_VALUE_FOR_MONEY, PRICE_NOT_VALID);
        validIntParam(Integer.parseInt(request.getParameter("discount")),MIN_VALUE_PERCENT, MAX_VALUE_PERCENT, DISCOUNT_NOT_VALID);
    }

    private void validIntParam(int param, int minValue, int maxValue, String message){
        if(param < minValue && param > maxValue){
            getValidationMessages().add(message);
        }
    }

    private void validLongParam(long param, long minValue, String message){
        if(param < minValue){
            getValidationMessages().add(message);
        }
    }

}
