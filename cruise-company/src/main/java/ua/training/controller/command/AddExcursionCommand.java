package ua.training.controller.command;

import ua.training.model.entity.Excursion;

import javax.servlet.http.HttpServletRequest;

public class AddExcursionCommand implements Command {
    private final ExcursionCommandUtility excursionCommandUtility;

    public AddExcursionCommand(ExcursionCommandUtility excursionCommandUtility) {
        this.excursionCommandUtility = excursionCommandUtility;
    }

    @Override
    public String execute(HttpServletRequest request) {
        //todo вытаскивание экскурсии по айди и полное сравнивание с тем что пришло в запросе
        CommandUtility.addExcursionToSelectedList(request,
                excursionCommandUtility.validAndGetExcursion(request));
        return "redirect:buy-submit-form";
    }

}
