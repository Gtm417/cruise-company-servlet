package ua.training.controller.command;

import ua.training.model.dto.OrderDTO;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Excursion;
import ua.training.model.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
                + ((OrderDTO) request.getSession().getAttribute("order")).getPrice());
        Cruise cruise = (Cruise) request.getSession().getAttribute("cruise");
        request.setAttribute("excursions", excursionService.showAllExcursionsInCruise(cruise.getId()));
        System.out.println(request.getSession().getAttribute("selectedExcursions"));
        return "buy-submit-form.jsp";
    }
}
