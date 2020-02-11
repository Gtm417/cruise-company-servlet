package ua.training.web.command;


import ua.training.exception.CruiseNotFoundException;
import ua.training.exception.TicketsEmptyListException;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ticket;
import ua.training.service.CruiseService;
import ua.training.service.TicketService;
import ua.training.web.handler.ExceptionHandler;

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
        long cruiseId = Long.parseLong(request.getParameter("cruiseId"));
        Cruise cruise;
        List<Ticket> cruiseTickets;
        try {
            cruise = cruiseService.findById(cruiseId);
            cruiseTickets = ticketService.showTicketsForBuy(cruiseId);
        } catch (TicketsEmptyListException | CruiseNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "buy.jsp");
            return exceptionHandler.handling(request);
        }
        if (cruiseService.checkAmountPassenger(cruise)) {
            request.setAttribute("noPlaces", true);
        }
        cruise.setTickets(cruiseTickets);
        request.getSession().setAttribute("cruise", cruise);
        return "buy.jsp";
    }


}
