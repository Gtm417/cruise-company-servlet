package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.CommandUtility;
import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.model.entity.Ticket;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AddTicketCommand implements Command {
    private final TicketService ticketService;
    private final RequestMapper<Ticket> ticketRequestMapper;

    public AddTicketCommand(TicketService ticketService, RequestMapper<Ticket> ticketRequestMapper) {
        this.ticketService = ticketService;
        this.ticketRequestMapper = ticketRequestMapper;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);

        //todo request Ticket Mapper
        String ticketName = request.getParameter("ticketName");
        String priceParam = request.getParameter("price");
        String discountParam = request.getParameter("discount");
        if (!valid(ticketName, priceParam, discountParam)) {
            return "add-ticket.jsp";
        }
        try {
            ticketService.addNewTicket(ticketRequestMapper.mapToEntity(request));
        } catch (DuplicateDataBaseException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "admin/add-ticket.jsp");
            return exceptionHandler.handling(request);

        }
        request.getSession().setAttribute("success", true);
        return "add-ticket.jsp";
    }

    private boolean valid(String ticketName, String price, String discount) {
        return !Objects.isNull(ticketName) && !Objects.isNull(price) && !Objects.isNull(discount);
    }
}
