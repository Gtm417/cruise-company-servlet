package ua.training.controller.command;

import ua.training.exception.ExcursionNotFound;
import ua.training.model.entity.Excursion;

import javax.servlet.http.HttpServletRequest;

public class RemoveExcursionCommand implements Command {

    private final ExcursionCommandUtility excursionCommandUtility;

    public RemoveExcursionCommand(ExcursionCommandUtility excursionCommandUtility) {
        this.excursionCommandUtility = excursionCommandUtility;
    }
    @Override
    public String execute(HttpServletRequest request) {
        try {
            CommandUtility.deleteExcursionFromSelectedList(request,
                    excursionCommandUtility.validAndGetExcursion(request));
        } catch (ExcursionNotFound ex) {
            //todo log
            ex.printStackTrace();
            request.setAttribute("exception", true);
        }
        return "redirect:buy-submit-form";

    }
}
