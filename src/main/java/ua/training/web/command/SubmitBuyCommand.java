package ua.training.web.command;

import ua.training.exception.SaveOrderException;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.service.OrderService;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.verification.request.Verifier;

import javax.servlet.http.HttpServletRequest;

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
        if (!orderService.subUserBalance(user, order.getOrderPrice())) {
            return "/WEB-INF/not-enough-money.jsp";
        }

        try {
            orderService.buyCruise(user, order);
        } catch (SaveOrderException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "buy-submit-form");
            return exceptionHandler.handling(request);
        }
        return "redirect:main";
    }


}
