package ua.training.controller.command;

import ua.training.controller.verification.request.Verifier;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;

public class SubmitBuyFormCommand implements Command {
    private final ExcursionService excursionService;
    private final Verifier verifier;

    public SubmitBuyFormCommand(ExcursionService excursionService, Verifier verifier) {

        this.excursionService = excursionService;
        this.verifier = verifier;
    }

    @Override
    public String execute(HttpServletRequest request) {
        verifier.verify(request);

        request.setAttribute("resultPrice",((Order) request.getSession().getAttribute("order")).getOrderPrice());
        request.setAttribute("excursions",
                excursionService.showAllExcursionsInCruise(((Cruise) request.getSession().getAttribute("cruise"))));

        return "buy-submit-form.jsp";
    }
}
