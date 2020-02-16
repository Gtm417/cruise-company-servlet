package ua.training.web.command;


import ua.training.entity.Excursion;
import ua.training.entity.Order;
import ua.training.exception.ExcursionNotFound;
import ua.training.service.ExcursionService;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.verification.request.Verifier;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static ua.training.web.CommandConstants.REDIRECT_COMMAND;

public class ExcursionCommand extends MultipleMethodCommand {

    private final ExcursionService excursionService;

    public ExcursionCommand(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        return null;
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        if (getVerifier().verify(request)) {
            return "WEB-INF/404.jsp";
        }

        Order order = ((Order) request.getSession().getAttribute("order"));
        Excursion excursion;
        try {
            excursion = excursionService.findById(Long.parseLong(request.getParameter("id")));
        } catch (ExcursionNotFound ex) {
            //todo log
            ExceptionHandler exceptionHandler = new ExceptionHandler(ex, "buy-submit", REDIRECT_COMMAND);
            return exceptionHandler.handling(request);
        }

        if (request.getRequestURI().contains("remove-excursion")) {
            order.getExcursionList().remove(excursion);
            return "redirect:buy-submit";
        }

        order.getExcursionList().add(excursion);
        return "redirect:buy-submit";
    }

    private Verifier getVerifier() {
        return request ->
                Objects.isNull(request.getSession().getAttribute("order"));
    }

}
