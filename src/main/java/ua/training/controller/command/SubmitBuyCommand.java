package ua.training.controller.command;

import ua.training.controller.handler.ExceptionHandler;
import ua.training.controller.verification.request.Verifier;
import ua.training.exception.NotEnoughMoney;
import ua.training.model.entity.Excursion;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SubmitBuyCommand implements Command {
    private final OrderService orderService;
    private final Verifier verifier;

    public SubmitBuyCommand(OrderService orderService, Verifier verifier) {
        this.orderService = orderService;
        this.verifier = verifier;
    }

    @Override
    public String execute(HttpServletRequest request) {
        verifier.verify(request);
        Order order = (Order) request.getSession().getAttribute("order");
        User user = (User) request.getSession().getAttribute("user");
        try {
            user.setBalance(orderService.subUserBalance(user, order.getOrderPrice()));
        } catch (NotEnoughMoney ex) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(ex, "buy-submit-form");
            return exceptionHandler.handling(request);
        }
        orderService.buyCruise(user, order);
        return "redirect:main";
    }


}
