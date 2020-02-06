package ua.training.controller.command;


import ua.training.controller.handler.ExceptionHandler;
import ua.training.exception.TicketsEmptyListException;
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
        try {
            List<Ticket> cruiseTickets = ticketService.showTicketsForBuy(CommandUtility.getCruiseId(request));
            request.setAttribute("tickets", cruiseTickets);
            request.getSession().setAttribute("cruise", cruiseTickets.get(0).getCruise());
        } catch (TicketsEmptyListException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "buy.jsp");
            return exceptionHandler.handling(request);
        }
        return "buy.jsp";
    }


}
