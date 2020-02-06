package ua.training.controller.command;


import ua.training.controller.handler.ExceptionHandler;
import ua.training.controller.verify.request.Verify;
import ua.training.exception.ExcursionNotFound;
import ua.training.model.entity.Excursion;
import ua.training.model.entity.Order;
import ua.training.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;

public class ExcursionCommand implements Command {
    private final Verify verify;
    private final ExcursionService excursionService;

    public ExcursionCommand(Verify verify, ExcursionService excursionService) {
        this.verify = verify;
        this.excursionService = excursionService;
    }



    @Override
    public String execute(HttpServletRequest request) {
        verify.verify(request);
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
        return  "redirect:buy-submit-form";
    }
}
