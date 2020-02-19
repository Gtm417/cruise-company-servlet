package ua.training.web.command;


import ua.training.entity.Excursion;
import ua.training.entity.Order;
import ua.training.exception.ExcursionNotFound;
import ua.training.service.ExcursionService;
import ua.training.web.handler.ExceptionHandler;
import ua.training.web.verification.request.Verifier;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static ua.training.web.AttributeConstants.SESSION_ORDER_ATTR;
import static ua.training.web.CommandConstants.*;
import static ua.training.web.PageConstants.PAGE_404_JSP;

public class ExcursionCommand implements Command {

    private final ExcursionService excursionService;

    public ExcursionCommand(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (getVerifier().verify(request)) {
            return PAGE_404_JSP;
        }

        Order order = ((Order) request.getSession().getAttribute(SESSION_ORDER_ATTR));
        Excursion excursion;
        try {
            excursion = excursionService.findById(Long.parseLong(request.getParameter("id")));
        } catch (ExcursionNotFound ex) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(ex, BUY_SUBMIT_COMMAND, REDIRECT_COMMAND);
            return exceptionHandler.handling(request);
        }

        if (request.getRequestURI().contains(REMOVE_EXCURSION_COMMAND)) {
            order.getExcursionList().remove(excursion);
            return REDIRECT_COMMAND + BUY_SUBMIT_COMMAND;
        }

        order.getExcursionList().add(excursion);
        return REDIRECT_COMMAND + BUY_SUBMIT_COMMAND;
    }

    private Verifier getVerifier() {
        return request ->
                Objects.isNull(request.getSession().getAttribute(SESSION_ORDER_ATTR));
    }

}
