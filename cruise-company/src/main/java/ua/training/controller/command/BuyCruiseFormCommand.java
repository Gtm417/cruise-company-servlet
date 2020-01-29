package ua.training.controller.command;


import ua.training.model.exception.TicketsEmptyListException;
import ua.training.model.service.CruiseService;

import javax.servlet.http.HttpServletRequest;

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
            request.setAttribute("tickets", cruiseService.showTicketsForBuy(cruiseId));
        } catch (TicketsEmptyListException e) {
            e.printStackTrace();
        }
        request.setAttribute("cruiseId", cruiseId);
        return "buy.jsp";
    }
}
