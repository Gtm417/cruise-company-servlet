package ua.training.web.command.admin;

import ua.training.exception.CruiseNotFoundException;
import ua.training.service.CruiseService;
import ua.training.web.command.MultipleMethodCommand;
import ua.training.web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class CruiseEditCommand extends MultipleMethodCommand {
    private final CruiseService cruiseService;

    public CruiseEditCommand(CruiseService cruiseService) {

        this.cruiseService = cruiseService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        if (request.getSession().getAttribute("cruise") != null) {
            return "edit-cruise.jsp";
        }
        try {
            request.getSession()
                    .setAttribute("cruise", cruiseService.findById(Long.parseLong(request.getParameter("cruiseId"))));
        } catch (CruiseNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "WEB-INF/404.jsp");
            return exceptionHandler.handling(request);
        }
        return "edit-cruise.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        return null;
    }
}
