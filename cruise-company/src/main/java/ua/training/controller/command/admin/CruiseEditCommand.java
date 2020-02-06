package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.controller.command.CommandUtility;
import ua.training.controller.handler.ExceptionHandler;
import ua.training.exception.CruiseNotFoundException;
import ua.training.service.CruiseService;

import javax.servlet.http.HttpServletRequest;

public class CruiseEditCommand implements Command {
    private final CruiseService cruiseService;

    public CruiseEditCommand(CruiseService cruiseService) {

        this.cruiseService = cruiseService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            request.getSession()
                    .setAttribute("cruise", cruiseService.findById(CommandUtility.getCruiseId(request)));
        } catch (CruiseNotFoundException e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "main");
            return exceptionHandler.handling(request);
        }
        return "edit-cruise.jsp";
    }
}
