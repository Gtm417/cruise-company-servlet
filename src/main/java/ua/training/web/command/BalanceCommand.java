package ua.training.web.command;

import ua.training.entity.User;
import ua.training.service.UserService;
import ua.training.web.form.validation.Validator;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;
import static ua.training.web.AttributeConstants.ERRORS_REQUEST_ATTR;
import static ua.training.web.AttributeConstants.SESSION_USER_ATTR;
import static ua.training.web.PageConstants.BALANCE_JSP;
import static ua.training.web.PageConstants.SUCCESS_JSP;

public class BalanceCommand extends MultipleMethodCommand {

    private static final String VALIDATION_REGEX = "([1-9][0-9]{0,3})";
    private static final int VALIDATION_MAX_VALUE = 9999;
    private static final int VALIDATION_MIN_VALUE = 0;
    private final UserService userService;

    public BalanceCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        return BALANCE_JSP;
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        if (!getValidator().validate(request)) {
            request.setAttribute(ERRORS_REQUEST_ATTR, true);
            return BALANCE_JSP;
        }
        userService.addBalance((User) request.getSession().getAttribute(SESSION_USER_ATTR),
                Long.parseLong(request.getParameter("balance")));
        return SUCCESS_JSP;
    }

    private Validator<HttpServletRequest> getValidator() {
        return this::validBalanceForm;
    }

    private boolean validBalanceForm(HttpServletRequest request) {
        String balance = request.getParameter("balance");
        if (isNull(balance) || !balance.matches(VALIDATION_REGEX)) {
            return true;
        }
        long balanceValue = Long.parseLong(balance);
        return balanceValue > VALIDATION_MIN_VALUE && balanceValue < VALIDATION_MAX_VALUE;
    }
}
