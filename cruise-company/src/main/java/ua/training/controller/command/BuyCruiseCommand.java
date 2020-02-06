package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.controller.command.validation.RequestParameterValidator;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.TicketNotFound;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;

public class BuyCruiseCommand implements Command {
    private final TicketService ticketService;
    private final RequestMapper<Order> orderRequestMapper;
    private final RequestParameterValidator validator;

    public BuyCruiseCommand(TicketService ticketService, RequestMapper<Order> orderRequestMapper, RequestParameterValidator validator) {

        this.ticketService = ticketService;
        this.orderRequestMapper = orderRequestMapper;
        this.validator = validator;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);

        if (!validator.validate(request).isEmpty()) {
            System.out.println("im in");
            System.out.println(validator.getValidationMessages());
            request.setAttribute("errors", validator.getValidationMessages());
            return "redirect:buy-form";
        }

        Order order = orderRequestMapper.mapToEntity(request);


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
