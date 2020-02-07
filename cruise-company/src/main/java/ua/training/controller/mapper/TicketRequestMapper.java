package ua.training.controller.mapper;

import ua.training.model.entity.Cruise;
import ua.training.model.entity.Ticket;

import javax.servlet.http.HttpServletRequest;

public class TicketRequestMapper implements RequestMapper<Ticket> {

    @Override
    public Ticket mapToEntity(HttpServletRequest request) {
        return Ticket.builder()
                .ticketName(request.getParameter("ticketName"))
                .price(Long.parseLong(request.getParameter("price")))
                .discount(Integer.parseInt(request.getParameter("discount")))
                .cruise((Cruise) request.getSession().getAttribute("cruise"))
                .build();
    }
}
