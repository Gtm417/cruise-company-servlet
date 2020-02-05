package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.TicketNotFound;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.model.entity.Ticket;
import ua.training.model.entity.User;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;

public class BuyCruiseCommand implements Command {
    private final TicketService ticketService;
    private final RequestMapper<Order> orderRequestMapper;

    public BuyCruiseCommand(TicketService ticketService, RequestMapper<Order> orderRequestMapper) {

        this.ticketService = ticketService;
        this.orderRequestMapper = orderRequestMapper;
    }

    @Override
    public String execute(HttpServletRequest request) {

        //todo OrderRequestMapper
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String ticketParam = request.getParameter("ticket");
        String stringCruiseId = request.getParameter("cruiseId");
        String priceParameter = request.getParameter("price");
        Order order = orderRequestMapper.mapToEntity(request);
        //todo validator
        if (firstName == null || secondName == null || ticketParam == null) {
            //todo тут должно быть сообщение на форме что пользователь не всё ввёл
            return "redirect:buy-form";
        }

        try {
            order.setTicket(ticketService.findTicketById(order.getTicket().getId()));
        } catch (TicketNotFound e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "buy-form");
            return exceptionHandler.handling(request);
        }

        request.getSession().setAttribute("order", order);
        CommandUtility.setSelectedExcursionsListToSession(request);
        return "redirect:buy-submit-form";
    }

}
