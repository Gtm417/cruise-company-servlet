package ua.training.controller.command;

import ua.training.model.entity.Excursion;
import ua.training.model.entity.Port;

import javax.servlet.http.HttpServletRequest;

public class AddExcursionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Excursion excursion = Excursion.builder()
                .id(Long.parseLong(request.getParameter("id")))
                .excursionName(request.getParameter("excursionName"))
                .price(Long.parseLong(request.getParameter("price")))
                .duration(Integer.parseInt(request.getParameter("duration")))
                .port(Port.builder()
                        .id(Long.parseLong(request.getParameter("portId")))
                        .portName(request.getParameter("portName"))
                        .build())
                .build();
        CommandUtility.addExcursionToSelectedList(request,excursion);
        return "buy-submit-form";
    }
}
