package ua.training.controller.command;

import ua.training.model.entity.Excursion;
import ua.training.model.entity.Port;
import ua.training.model.exception.UnreachableRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ExcursionCommandUtility {
    //todo refactoring
    //i can all take from request
    // first valid after getExcursion
    static Excursion validAndGetExcursion(HttpServletRequest request) {
        String id = request.getParameter("id");
        String excursionName = request.getParameter("excursionName");
        String price = request.getParameter("price");
        String duration = request.getParameter("duration");
        String portId = request.getParameter("portId");
        String portName = request.getParameter("portName");

        boolean validData = Objects.isNull(id) ||  Objects.isNull(excursionName)
                ||  Objects.isNull(portId) ||  Objects.isNull(duration)
                ||  Objects.isNull(price) ||  Objects.isNull(portName);
        System.out.println("VALID: " + validData);

        if(validData || Objects.isNull(request.getSession().getAttribute("selectedExcursions"))){
            request.getSession().setAttribute("notEnoughData", true);
            throw new UnreachableRequest();
        }

        return Excursion.builder()
                .id(Long.parseLong(id))
                .excursionName(excursionName)
                .price(Long.parseLong(price))
                .duration(Integer.parseInt(duration))
                .port(Port.builder()
                        .id(Long.parseLong(portId))
                        .portName(portName)
                        .build())
                .build();
    }

}
