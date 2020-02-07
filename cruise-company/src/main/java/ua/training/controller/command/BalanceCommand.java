package ua.training.controller.command;

import ua.training.controller.validation.RequestParameterValidator;
import ua.training.model.entity.User;
import ua.training.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class BalanceCommand implements Command {

    private final UserService userService;
    private final RequestParameterValidator balanceValidator;

    public BalanceCommand(UserService userService, RequestParameterValidator balanceValidator) {
        this.userService = userService;
        this.balanceValidator = balanceValidator;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if(!balanceValidator.validate(request).isEmpty()){
            request.setAttribute("exception", true);
            return "balance.jsp";
        }
        userService.addBalance((User) request.getSession().getAttribute("user"),
                Long.parseLong(request.getParameter("balance")));
        return "success-replenish.jsp";
    }
}
