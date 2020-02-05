package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.exception.CruiseNotFoundException;
import ua.training.service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CruiseEditCommand implements Command {
    private final CruiseService cruiseService;

    public CruiseEditCommand(CruiseService cruiseService) {

        this.cruiseService = cruiseService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String cruiseIdParam = request.getParameter("cruiseId");
        //mb not needed
        if (Objects.isNull(cruiseIdParam)) {
            request.getSession().setAttribute("notFoundCruise", true);
            return "redirect:main";
        }
        try {
            request.getSession()
                    .setAttribute("cruise", cruiseService.findById(Long.parseLong(cruiseIdParam)));
        } catch (CruiseNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "admin/edit-cruise.jsp");
            return exceptionHandler.handling(request);
        }
        return "edit-cruise.jsp";
    }
}
