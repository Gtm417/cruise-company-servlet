package ua.training.web.command.admin;

import ua.training.entity.Cruise;
import ua.training.service.OrderService;
import ua.training.web.command.CommandUtility;
import ua.training.web.command.MultipleMethodCommand;

import javax.servlet.http.HttpServletRequest;

public class AllPassengersCommand extends MultipleMethodCommand {
    private final OrderService orderService;

    public AllPassengersCommand(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    protected String performGet(HttpServletRequest request) {
        Cruise cruise = CommandUtility.checkCruiseInSession(request);
        request.setAttribute("passengers", orderService.showAllPassengersOnCruise(cruise.getId()));
        return "all-passengers.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        return null;
    }
}
