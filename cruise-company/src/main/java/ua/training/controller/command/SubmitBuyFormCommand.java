package ua.training.controller.command;

import ua.training.controller.verification.request.Verify;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;

public class SubmitBuyFormCommand implements Command {
    private final ExcursionService excursionService;
    private final Verify verify;

    public SubmitBuyFormCommand(ExcursionService excursionService, Verify verify) {

        this.excursionService = excursionService;
        this.verify = verify;
    }

    @Override
    public String execute(HttpServletRequest request) {
        verify.verify(request);

        Order order = (Order) request.getSession().getAttribute("order");
        request.setAttribute("resultPrice", order.getOrderPrice());
        Cruise cruise = (Cruise) request.getSession().getAttribute("cruise");
        request.setAttribute("excursions", excursionService.showAllExcursionsInCruise(cruise.getId()));

        return "buy-submit-form.jsp";
    }
}
