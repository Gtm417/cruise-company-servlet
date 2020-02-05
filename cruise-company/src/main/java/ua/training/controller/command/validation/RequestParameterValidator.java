package ua.training.controller.command.validation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Objects.isNull;

public abstract class  RequestParameterValidator {

    private List<String> validationMessages;

    public List<String> getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(List<String> validationMessages) {
        this.validationMessages = validationMessages;
    }

    public abstract List<String> validate(HttpServletRequest request);

    public void validStringParam(String param, String regex, String message){
        if(isNull(param) || !param.matches(regex)){
            validationMessages.add(message);
        }
    }


}
