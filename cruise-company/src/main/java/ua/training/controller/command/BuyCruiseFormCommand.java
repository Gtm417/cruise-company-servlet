package ua.training.controller.command;


import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.model.dto.TicketCruiseDTO;
import ua.training.model.exception.TicketsEmptyListException;
import ua.training.model.service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BuyCruiseFormCommand implements Command {
    private final CruiseService cruiseService;

    public BuyCruiseFormCommand() {
        this.cruiseService = new CruiseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String stringCruiseId = request.getParameter("cruiseId");
        System.out.println("cruise id  " + stringCruiseId);

        if(stringCruiseId == null) {
            //человек не понятным образом попал на запрос buyform, круиза в запросе не было поэтому он возвращается на мейн
            return "redirect:main";
        }
        long cruiseId = Long.parseLong(stringCruiseId);
        try {
            List<TicketCruiseDTO> cruiseTickets = cruiseService.showTicketsForBuy(cruiseId);
            request.setAttribute("tickets", cruiseTickets);
            request.getSession().setAttribute("cruise", cruiseTickets.get(0).getCruise());
        } catch (TicketsEmptyListException e) {
            ExceptionHandler exceptionHandler =  new ExceptionHandler(e, "buy.jsp");
            return exceptionHandler.handling(request);
        }
        request.setAttribute("cruiseId", cruiseId);
        return "buy.jsp";
    }
}
