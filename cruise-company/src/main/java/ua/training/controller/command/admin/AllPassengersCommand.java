package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.CommandUtility;
import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.model.entity.Cruise;
import ua.training.model.exception.EmptyPassengerList;
import ua.training.model.service.CruiseService;
import ua.training.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class AllPassengersCommand implements Command {
    private final OrderService orderService;

    public AllPassengersCommand() {
        this.orderService = new OrderService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);
        try {
            request.setAttribute("passengers", orderService.showAllPassengersOnCruise(((Cruise) request.getSession().getAttribute("cruise")).getId()));
        } catch (EmptyPassengerList e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "admin/all-passengers.jsp");
            return exceptionHandler.handling(request);
        }
        return "all-passengers.jsp";
    }
}
