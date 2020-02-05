package ua.training.controller.command;

import ua.training.controller.mapper.RequestMapper;
import ua.training.exception.UnreachableRequest;
import ua.training.model.entity.Excursion;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ExcursionCommandUtility {
    private final RequestMapper<Excursion> excursionRequestMapper;

    public ExcursionCommandUtility(RequestMapper<Excursion> excursionRequestMapper) {
        this.excursionRequestMapper = excursionRequestMapper;
    }

     public Excursion validAndGetExcursion(HttpServletRequest request) {

        //todo ExcursionRequestMapper
        String id = request.getParameter("id");
        String excursionName = request.getParameter("excursionName");
        String price = request.getParameter("price");
        String duration = request.getParameter("duration");
        String portId = request.getParameter("portId");
        String portName = request.getParameter("portName");

        boolean validData = Objects.isNull(id) || Objects.isNull(excursionName)
                || Objects.isNull(portId) || Objects.isNull(duration)
                || Objects.isNull(price) || Objects.isNull(portName);

        //todo Validator Excursion
        if (validData || Objects.isNull(request.getSession().getAttribute("selectedExcursions"))) {
            request.getSession().setAttribute("notEnoughData", true);
            throw new UnreachableRequest();
        }


        return excursionRequestMapper.mapToEntity(request);
    }

}
