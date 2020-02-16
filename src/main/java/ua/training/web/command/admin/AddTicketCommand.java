package ua.training.web.command.admin;

import ua.training.entity.Cruise;
import ua.training.entity.Ticket;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.service.TicketService;
import ua.training.web.command.CommandUtility;
import ua.training.web.command.MultipleMethodCommand;
import ua.training.web.form.TicketForm;
import ua.training.web.form.validation.Validator;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.mapper.MapperFormToEntity;
import ua.training.web.mapper.RequestFormMapper;

import javax.servlet.http.HttpServletRequest;

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
        return "add-ticket.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);

        TicketForm ticketForm = getRequestMapper().mapToForm(request);

        if (!validator.validate(ticketForm)) {
            request.setAttribute("errors", true);
            return "add-ticket.jsp";
        }

        try {
            ticketService.addNewTicket(getFormEntityMapper(request).mapToEntity(ticketForm));
        } catch (DuplicateDataBaseException e) {
            e.printStackTrace();
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "admin/add-ticket.jsp");
            return exceptionHandler.handling(request);

        }
        request.getSession().setAttribute("success", true);
        return "add-ticket.jsp";
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
                .cruise((Cruise) request.getSession().getAttribute("cruise"))
                .build();
    }
}
