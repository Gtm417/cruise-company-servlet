package ua.training.controller.command;

import ua.training.model.dto.OrderDTO;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Excursion;
import ua.training.model.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SubmitBuyFormCommand implements Command {
    private final ExcursionService excursionService;

    public SubmitBuyFormCommand() {
        this.excursionService = new ExcursionService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        if(request.getSession().getAttribute("order") == null
                || request.getSession().getAttribute("cruise") == null
                || request.getSession().getAttribute("selectedExcursions") == null){
            return "redirect:main";
        }
        //((List<Excursion>)request.getSession().getAttribute("selectedExcursions")).add(Excursion.builder().excursionName("name").price(100).build());
        request.setAttribute("resultPrice", CommandUtility.countSelectedExcursionsPrice(request)
                + ((OrderDTO) request.getSession().getAttribute("order")).getPrice());
        Cruise cruise = (Cruise) request.getSession().getAttribute("cruise");
        request.setAttribute("excursions", excursionService.showAllExcursionsInCruise(cruise.getId()));
        return "buy-submit-form.jsp";
    }
}
