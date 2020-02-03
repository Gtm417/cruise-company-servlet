package ua.training.controller.command;

import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.model.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SubmitBuyFormCommand implements Command {
    private final ExcursionService excursionService;

    public SubmitBuyFormCommand() {
        this.excursionService = new ExcursionService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        if (Objects.isNull(request.getSession().getAttribute("order"))
                || Objects.isNull(request.getSession().getAttribute("cruise"))
                || Objects.isNull(request.getSession().getAttribute("selectedExcursions"))) {
            request.setAttribute("notAllData", true);
            return "redirect:main";
        }
        request.setAttribute("resultPrice", CommandUtility.countSelectedExcursionsPrice(request)
                + ((Order) request.getSession().getAttribute("order")).getOrderPrice());
        Cruise cruise = (Cruise) request.getSession().getAttribute("cruise");
        request.setAttribute("excursions", excursionService.showAllExcursionsInCruise(cruise.getId()));
        return "buy-submit-form.jsp";
    }
}
