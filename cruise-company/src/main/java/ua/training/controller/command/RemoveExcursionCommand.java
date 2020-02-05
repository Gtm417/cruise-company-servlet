package ua.training.controller.command;

import ua.training.model.entity.Excursion;

import javax.servlet.http.HttpServletRequest;

public class RemoveExcursionCommand implements Command {

    private final ExcursionCommandUtility excursionCommandUtility;

    public RemoveExcursionCommand(ExcursionCommandUtility excursionCommandUtility) {
        this.excursionCommandUtility = excursionCommandUtility;
    }
    @Override
    public String execute(HttpServletRequest request) {
        //todo вытаскивание экскурсии по айди и полное сравнивание с тем что пришло в запросе
        CommandUtility.deleteExcursionFromSelectedList(request,
                excursionCommandUtility.validAndGetExcursion(request));
        return "redirect:buy-submit-form";
    }
}
