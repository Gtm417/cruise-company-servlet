package ua.training.web.command;


import ua.training.entity.Excursion;
import ua.training.entity.Order;
import ua.training.exception.ExcursionNotFound;
import ua.training.exception.UnreachableRequest;
import ua.training.service.ExcursionService;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.verification.request.Verifier;

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
            //todo return 404
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
            order.getExcursionList().remove(excursion);
            return "redirect:buy-submit-form";
        }
        order.getExcursionList().add(excursion);
        return "redirect:buy-submit-form";
    }

    private Verifier getVerifier() {
        return request ->
                Objects.isNull(request.getSession().getAttribute("order"));
    }

}
