package ua.training.web.command.admin;

import ua.training.exception.CruiseNotFoundException;
import ua.training.service.CruiseService;
import ua.training.web.AttributeConstants;
import ua.training.web.command.Command;
import ua.training.web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.SESSION_CRUISE_ATTR;
import static ua.training.web.PageConstants.EDIT_CRUISE_JSP;
import static ua.training.web.PageConstants.PAGE_404_JSP;

public class CruiseEditCommand implements Command {
    private final CruiseService cruiseService;

    public CruiseEditCommand(CruiseService cruiseService) {

        this.cruiseService = cruiseService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute(SESSION_CRUISE_ATTR) != null) {
            return EDIT_CRUISE_JSP;
        }
        try {
            request.getSession()
                    .setAttribute(AttributeConstants.CRUISE_REQUEST_ATTR, cruiseService.findById(Long.parseLong(request.getParameter("cruiseId"))));
        } catch (CruiseNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, PAGE_404_JSP);
            return exceptionHandler.handling(request);
        }
        return EDIT_CRUISE_JSP;
    }

}
