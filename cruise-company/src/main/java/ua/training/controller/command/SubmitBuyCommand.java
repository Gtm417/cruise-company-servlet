package ua.training.controller.command;

import ua.training.controller.handler.ExceptionHandler;
import ua.training.exception.NotEnoughMoney;
import ua.training.exception.UnreachableRequest;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SubmitBuyCommand implements Command {
    private final OrderService orderService;

    public SubmitBuyCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        valid(request);
        long resultPrice = Long.parseLong(request.getParameter("resultPrice"));

        Order order = (Order) request.getSession().getAttribute("order");
        order.setOrderPrice(resultPrice);

        User user = (User) request.getSession().getAttribute("user");

        try {
            user.setBalance(orderService.subUserBalance(user, resultPrice));
        } catch (NotEnoughMoney ex) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(ex, "buy-submit-form");
            return exceptionHandler.handling(request);
        }
        orderService.buyCruise(user, order);
        return "redirect:main";
    }

    //todo default null validator (verify)
    private boolean valid(HttpServletRequest request) {
        if (Objects.isNull(request.getParameter("resultPrice"))
                || Objects.isNull(request.getSession().getAttribute("order"))) {
            throw new UnreachableRequest();
        }
        return true;
    }

}
