package ua.training.web.command;

import ua.training.exception.EmptyOrderListException;
import ua.training.entity.User;
import ua.training.service.OrderService;
import ua.training.web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class AllOrdersCommand implements Command {
    private final OrderService orderService;

    public AllOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            request.setAttribute("orders", orderService.showAllUserOrders(
                    ((User) request.getSession().getAttribute("user")).getId()));
        } catch (EmptyOrderListException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "all-orders.jsp");
            return exceptionHandler.handling(request);
        }
        return "all-orders.jsp";
    }
}
