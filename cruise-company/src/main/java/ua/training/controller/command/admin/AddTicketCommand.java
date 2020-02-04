package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.CommandUtility;
import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.exception.DuplicateDataBaseException;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ticket;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AddTicketCommand implements Command {
    private final TicketService ticketService;

    public AddTicketCommand(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);
        String ticketName = request.getParameter("ticketName");
        String priceParam = request.getParameter("price");
        String discountParam = request.getParameter("discount");
        if (!valid(ticketName, priceParam, discountParam)) {
            return "add-ticket.jsp";
        }
        try {
            ticketService.addNewTicket(Ticket.builder()
                    .ticketName(ticketName)
                    .price(Long.parseLong(priceParam))
                    .discount(Integer.parseInt(discountParam))
                    .cruise((Cruise) request.getSession().getAttribute("cruise"))
                    .build());
        } catch (DuplicateDataBaseException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "admin/add-ticket.jsp");
            return exceptionHandler.handling(request);

        }
        request.getSession().setAttribute("success", true);
        return "add-ticket.jsp";
    }

    private boolean valid(String ticketName, String price, String discount) {
        if (Objects.isNull(ticketName) || Objects.isNull(price) || Objects.isNull(discount)) {
            return false;
        }
        return true;
    }
}
