package ua.training.controller.command;

import ua.training.controller.handler.ExceptionHandler;
import ua.training.controller.verification.request.Verify;
import ua.training.exception.NotEnoughMoney;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class SubmitBuyCommand implements Command {
    private final OrderService orderService;
    private final Verify verify;

    public SubmitBuyCommand(OrderService orderService, Verify verify) {

        this.orderService = orderService;
        this.verify = verify;
    }

    @Override
    public String execute(HttpServletRequest request) {
        verify.verify(request);
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
