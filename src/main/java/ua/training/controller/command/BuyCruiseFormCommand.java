package ua.training.controller.command;


import ua.training.controller.handler.ExceptionHandler;
import ua.training.exception.CruiseNotFoundException;
import ua.training.exception.TicketsEmptyListException;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ticket;
import ua.training.service.CruiseService;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BuyCruiseFormCommand implements Command {
    private final TicketService ticketService;
    private final CruiseService cruiseService;

    public BuyCruiseFormCommand(TicketService ticketService, CruiseService cruiseService) {
        this.ticketService = ticketService;
        this.cruiseService = cruiseService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("cruise") != null) {
            return "buy.jsp";
        }
        long cruiseId  = Long.parseLong(request.getParameter("cruiseId"));
        try {
            Cruise cruise = cruiseService.findById(cruiseId);
            List<Ticket> cruiseTickets = ticketService.showTicketsForBuy(cruiseId);
            cruise.setTickets(cruiseTickets);
            request.getSession().setAttribute("cruise", cruise);
        } catch (TicketsEmptyListException | CruiseNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "buy.jsp");
            return exceptionHandler.handling(request);
        }
        return "buy.jsp";
    }


}
