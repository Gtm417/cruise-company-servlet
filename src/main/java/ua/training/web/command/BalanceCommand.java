package ua.training.web.command;

import ua.training.model.entity.User;
import ua.training.service.UserService;
import ua.training.web.form.validation.Validator;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;

public class BalanceCommand implements Command {

    public static final String VALIDATION_REGEX = "[0-9].{0,4}";
    public static final int VALIDATION_MAX_VALUE = 9999;
    public static final int VALIDATION_MIN_VALUE = 0;
    private final UserService userService;

    public BalanceCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!getValidator().validate(request)) {
            request.setAttribute("errors", true);
            return "balance.jsp";
        }
        userService.addBalance((User) request.getSession().getAttribute("user"),
                Long.parseLong(request.getParameter("balance")));
        return "success-replenish.jsp";
    }

    private Validator<HttpServletRequest> getValidator() {
        return this::validBalanceForm;
    }

    private boolean validBalanceForm(HttpServletRequest request) {
        String balance = request.getParameter("balance");
        if (isNull(balance) || !balance.matches(VALIDATION_REGEX)) {
            return false;
        }
        long balanceValue = Long.parseLong(balance);
        return balanceValue < VALIDATION_MIN_VALUE || balanceValue > VALIDATION_MAX_VALUE;
    }
}
