package ua.training.controller.command.validation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RequestParameterValidator<T> {
    List<String> validate(HttpServletRequest request);
    List<String> getValidationMessages();
}
