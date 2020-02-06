package ua.training.controller.command;

import ua.training.exception.ExcursionNotFound;

import javax.servlet.http.HttpServletRequest;

public class AddExcursionCommand implements Command {
    private final ExcursionCommandUtility excursionCommandUtility;

    public AddExcursionCommand(ExcursionCommandUtility excursionCommandUtility) {
        this.excursionCommandUtility = excursionCommandUtility;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            CommandUtility.addExcursionToSelectedList(request,
                    excursionCommandUtility.validAndGetExcursion(request));
        } catch (ExcursionNotFound ex) {
            //todo log
            ex.printStackTrace();
            request.setAttribute("exception", true);
        }
        return "redirect:buy-submit-form";
    }

}
