package ua.training.web.command.admin;

import ua.training.entity.Cruise;
import ua.training.entity.Ticket;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.service.TicketService;
import ua.training.web.PageConstants;
import ua.training.web.command.CommandUtility;
import ua.training.web.command.MultipleMethodCommand;
import ua.training.web.form.TicketForm;
import ua.training.web.form.validation.Validator;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.mapper.MapperFormToEntity;
import ua.training.web.mapper.RequestFormMapper;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.*;
import static ua.training.web.PageConstants.ADD_TICKET_JSP;

public class AddTicketCommand extends MultipleMethodCommand {

    private final TicketService ticketService;

    private final Validator<TicketForm> validator;

    public AddTicketCommand(TicketService ticketService, Validator<TicketForm> validator) {
        this.ticketService = ticketService;
        this.validator = validator;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);
        return ADD_TICKET_JSP;
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);

        TicketForm ticketForm = getRequestMapper().mapToForm(request);

        if (!validator.validate(ticketForm)) {
            request.setAttribute(ERRORS_REQUEST_ATTR, true);
            return ADD_TICKET_JSP;
        }

        try {
            ticketService.addNewTicket(getFormEntityMapper(request).mapToEntity(ticketForm));
        } catch (DuplicateDataBaseException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, PageConstants.ADMIN_ADD_TICKET_JSP);
            return exceptionHandler.handling(request);
        }
        request.getSession().setAttribute(SUCCESS_SESSION_ATTR, true);
        return ADD_TICKET_JSP;
    }

    private RequestFormMapper<TicketForm> getRequestMapper() {
        return request -> new TicketForm(request.getParameter("ticketName"),
                request.getParameter("price"),
                request.getParameter("discount"));
    }

    private MapperFormToEntity<Ticket, TicketForm> getFormEntityMapper(HttpServletRequest request) {
        return form -> Ticket.builder()
                .ticketName(form.getName())
                .price(Long.parseLong(form.getPrice()))
                .discount(Integer.parseInt(form.getDiscount()))
                .cruise((Cruise) request.getSession().getAttribute(SESSION_CRUISE_ATTR))
                .build();
    }
}
