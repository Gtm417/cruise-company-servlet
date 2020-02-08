package ua.training.controller.command;


import ua.training.controller.handler.ExceptionHandler;
import ua.training.controller.verification.request.Verifier;
import ua.training.exception.ExcursionNotFound;
import ua.training.exception.UnreachableRequest;
import ua.training.model.entity.Excursion;
import ua.training.model.entity.Order;
import ua.training.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ExcursionCommand implements Command {
    private final ExcursionService excursionService;

    public ExcursionCommand(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        if (getVerifier().verify(request)) {
            throw new UnreachableRequest();
        }

        Order order = ((Order) request.getSession().getAttribute("order"));
        Excursion excursion;
        try {
            excursion = excursionService.findById(Long.parseLong(request.getParameter("id")));
        } catch (ExcursionNotFound ex) {
            //todo log
            ExceptionHandler exceptionHandler = new ExceptionHandler(ex, "buy-submit-form");
            return exceptionHandler.handling(request);
        }
        if (request.getRequestURI().contains("remove-excursion")) {
            CommandUtility.deleteExcursionFromSelectedList(request, excursion);
            order.setOrderPrice(order.getOrderPrice() - excursion.getPrice());
            return "redirect:buy-submit-form";
        }
        CommandUtility.addExcursionToSelectedList(request, excursion);
        order.setOrderPrice(order.getOrderPrice() + excursion.getPrice());
        return "redirect:buy-submit-form";
    }

    private Verifier getVerifier() {
        return request -> Objects.isNull(request.getSession().getAttribute("selectedExcursions"));
    }

}
