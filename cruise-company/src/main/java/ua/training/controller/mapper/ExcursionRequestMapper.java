package ua.training.controller.mapper;

import ua.training.model.entity.Excursion;
import ua.training.model.entity.Port;

import javax.servlet.http.HttpServletRequest;

public class ExcursionRequestMapper implements RequestMapper<Excursion> {
    @Override
    public Excursion mapToEntity(HttpServletRequest request) {
        return Excursion.builder()
                .id(Long.parseLong(request.getParameter("id")))
                .excursionName(request.getParameter("excursionName"))
                .price(Long.parseLong(request.getParameter("price")))
                .duration(Integer.parseInt(request.getParameter("duration")))
                .port(Port.builder()
                        .id(Long.parseLong( request.getParameter("portId")))
                        .portName(request.getParameter("portName"))
                        .build())
                .build();
    }
}
