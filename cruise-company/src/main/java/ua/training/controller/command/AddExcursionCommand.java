package ua.training.controller.command;

import ua.training.model.entity.Excursion;

import javax.servlet.http.HttpServletRequest;

public class AddExcursionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        //todo вытаскивание экскурсии по айди и полное сравнивание с тем что пришло в запросе
        Excursion excursion = ExcursionCommandUtility.validAndGetExcursion(request);
        CommandUtility.addExcursionToSelectedList(request, excursion);
        return "redirect:buy-submit-form";
    }

}
