package ua.training.controller.command;

import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class BalanceCommand implements Command {

    UserService userService;

    public BalanceCommand() {
        this.userService = new UserService();
    }
    @Override
    public String execute(HttpServletRequest request) {
        String stringValue = request.getParameter("balance");
        if(Objects.isNull(stringValue)) {
            return "balance.jsp";
        }
        long value = Long.parseLong(request.getParameter("balance"));
        userService.addBalance((User)request.getSession().getAttribute("user"),value);
        return "success-replenish.jsp";
    }
}
