package ua.training.web.command;

import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.service.ExcursionService;
import ua.training.web.verification.request.Verifier;

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

        Order order = (Order) request.getSession().getAttribute("order");
        order.setOrderPrice(order.getTicket().getPriceWithDiscount()
                + excursionService.getTotalSumExcursionSet(order.getExcursionList()));

        request.setAttribute("resultPrice", order.getOrderPrice());
        request.setAttribute("excursions",
                excursionService.showAllExcursionsInCruise(((Cruise) request.getSession().getAttribute("cruise"))));

        return "buy-submit-form.jsp";
    }
}
