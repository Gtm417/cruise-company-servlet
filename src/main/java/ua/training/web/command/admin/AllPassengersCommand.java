package ua.training.web.command.admin;

import ua.training.exception.EmptyOrderListException;
import ua.training.entity.Cruise;
import ua.training.service.OrderService;
import ua.training.web.command.Command;
import ua.training.web.command.CommandUtility;
import ua.training.web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class AllPassengersCommand implements Command {
    private final OrderService orderService;

    public AllPassengersCommand(OrderService orderService) {

        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Cruise cruise = CommandUtility.checkCruiseInSession(request);
        try {
            request.setAttribute("passengers", orderService.showAllPassengersOnCruise(cruise.getId()));
        } catch (EmptyOrderListException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "admin/all-passengers.jsp");
            return exceptionHandler.handling(request);
        }
        return "all-passengers.jsp";
    }
}
