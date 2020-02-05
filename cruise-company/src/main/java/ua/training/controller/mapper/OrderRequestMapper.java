package ua.training.controller.mapper;

import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.model.entity.Ticket;
import ua.training.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class OrderRequestMapper implements RequestMapper<Order> {
    @Override
    public Order mapToEntity(HttpServletRequest request) {

        return Order.builder()
                .firstName(request.getParameter("firstName"))
                .secondName(request.getParameter("secondName"))
                .orderPrice(Long.parseLong(request.getParameter("price")))
                .cruise((Cruise) request.getSession().getAttribute("cruise"))
                .user((User)request.getSession().getAttribute("user"))
                .ticket(Ticket.builder().id(Long.parseLong(request.getParameter("ticket"))).build())
                .build();
    }
}
