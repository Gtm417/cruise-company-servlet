package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.model.entity.User;
import ua.training.model.exception.EmptyOrderListException;
import ua.training.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class AllOrdersCommand implements Command {
    private final OrderService orderService;

    public AllOrdersCommand() {
        this.orderService = new OrderService();
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
