package ua.training.controller.command;


import ua.training.controller.handler.ExceptionHandler;
import ua.training.exception.TicketsEmptyListException;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ticket;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BuyCruiseFormCommand implements Command {
    private final TicketService ticketService;

    public BuyCruiseFormCommand(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("cruise") != null) {
            return "buy.jsp";
        }
        try {
            List<Ticket> cruiseTickets = ticketService.showTicketsForBuy(Long.parseLong(request.getParameter("cruiseId")));

            Cruise cruise = cruiseTickets.get(0).getCruise();
            cruise.setTickets(cruiseTickets);
            request.getSession().setAttribute("cruise", cruise);
        } catch (TicketsEmptyListException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "buy.jsp");
            return exceptionHandler.handling(request);
        }
        return "buy.jsp";
    }


}
