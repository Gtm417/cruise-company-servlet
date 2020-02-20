package ua.training.web.command.admin;

import ua.training.entity.Cruise;
import ua.training.service.OrderService;
import ua.training.web.command.Command;
import ua.training.web.command.CommandUtility;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.PASSENGERS_REQUEST_ATTR;
import static ua.training.web.PageConstants.ALL_PASSENGERS_JSP;

public class AllPassengersCommand implements Command {
    private final OrderService orderService;

    public AllPassengersCommand(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        Cruise cruise = CommandUtility.checkCruiseInSession(request);
        request.setAttribute(PASSENGERS_REQUEST_ATTR, orderService.showAllPassengersOnCruise(cruise.getId()));
        return ALL_PASSENGERS_JSP;
    }
}
