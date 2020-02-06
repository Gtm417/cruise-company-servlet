package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.CommandUtility;
import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.exception.EmptyOrderListException;
import ua.training.model.entity.Cruise;
import ua.training.service.OrderService;

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
