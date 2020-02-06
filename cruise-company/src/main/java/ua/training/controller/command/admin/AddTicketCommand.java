package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.CommandUtility;
import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.controller.command.validation.RequestParameterValidator;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.model.entity.Ticket;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AddTicketCommand implements Command {
    private final TicketService ticketService;
    private final RequestMapper<Ticket> ticketRequestMapper;
    private final RequestParameterValidator requestValidator;

    public AddTicketCommand(TicketService ticketService, RequestMapper<Ticket> ticketRequestMapper, RequestParameterValidator requestValidator) {
        this.ticketService = ticketService;
        this.ticketRequestMapper = ticketRequestMapper;
        this.requestValidator = requestValidator;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);

        if(!requestValidator.validate(request).isEmpty()){
            System.out.println(requestValidator.getValidationMessages());
            request.setAttribute("errors", requestValidator.getValidationMessages());
            return "add-ticket.jsp";
        }

        try {
            ticketService.addNewTicket(ticketRequestMapper.mapToEntity(request));
        } catch (DuplicateDataBaseException e) {
            e.printStackTrace();
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "admin/add-ticket.jsp");
            return exceptionHandler.handling(request);

        }
        request.getSession().setAttribute("success", true);
        return "add-ticket.jsp";
    }

}
