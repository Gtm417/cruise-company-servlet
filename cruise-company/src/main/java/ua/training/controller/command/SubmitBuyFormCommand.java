package ua.training.controller.command;

import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SubmitBuyFormCommand implements Command {
    private final ExcursionService excursionService;

    public SubmitBuyFormCommand(ExcursionService excursionService) {

        this.excursionService = excursionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        // todo default null validator
        if (Objects.isNull(request.getSession().getAttribute("order"))
                || Objects.isNull(request.getSession().getAttribute("cruise"))
                || Objects.isNull(request.getSession().getAttribute("selectedExcursions"))) {
            request.setAttribute("notAllData", true);
            return "redirect:main";
        }

        Order order = (Order) request.getSession().getAttribute("order");
        order.setOrderPrice(order.getTicket().getPriceWithDiscount());
        request.setAttribute("resultPrice", CommandUtility.countOrderPrice(request));
        Cruise cruise = (Cruise) request.getSession().getAttribute("cruise");
        request.setAttribute("excursions", excursionService.showAllExcursionsInCruise(cruise.getId()));

        return "buy-submit-form.jsp";
    }
}
