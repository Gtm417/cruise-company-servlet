package ua.training.web.command;

import ua.training.entity.Cruise;
import ua.training.entity.Order;
import ua.training.entity.User;
import ua.training.exception.CruiseNotFoundException;
import ua.training.exception.TicketNotFound;
import ua.training.service.CruiseService;
import ua.training.service.TicketService;
import ua.training.web.form.OrderForm;
import ua.training.web.form.validation.Validator;
import ua.training.web.mapper.MapperFormToEntity;
import ua.training.web.mapper.RequestFormMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class BuyCruiseCommand extends MultipleMethodCommand {

    private final TicketService ticketService;
    private final CruiseService cruiseService;
    private final Validator<OrderForm> validator;

    public BuyCruiseCommand(TicketService ticketService, CruiseService cruiseService, Validator<OrderForm> validator) {
        this.ticketService = ticketService;
        this.cruiseService = cruiseService;
        this.validator = validator;
    }

    @Override
    protected String performGet(HttpServletRequest request) {

        if (request.getSession().getAttribute("cruise") != null) {
            Cruise cruise = (Cruise) request.getSession().getAttribute("cruise");
            cruise.setTickets(ticketService.findAllTicketsForBuy(cruise.getId()));
            return "buy.jsp";
        }

        long cruiseId = Long.parseLong(request.getParameter("cruiseId"));

        Cruise cruise;
        try {
            cruise = cruiseService.findById(cruiseId);
        } catch (CruiseNotFoundException e) {
            //todo log
            return "WEB-INF/404.jsp";
        }

        if (!cruiseService.checkAmountPassenger(cruise)) {
            request.setAttribute("noPlaces", true);
        }

        cruise.setTickets(ticketService.findAllTicketsForBuy(cruiseId));
        request.getSession().setAttribute("cruise", cruise);
        return "buy.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {

        CommandUtility.checkCruiseInSession(request);
        OrderForm orderForm = getRequestFormMapper().mapToForm(request);
        if (validator.validate(orderForm)) {
            request.setAttribute("errors", true);
            return "buy.jsp";
        }

        Order order = getFormEntityMapper(request).mapToEntity(orderForm);

        try {
            order.setTicket(ticketService.findTicketByIdAndCruise(Long.parseLong(orderForm.getTicket()), order.getCruise().getId()));
        } catch (TicketNotFound e) {
            //todo log
            return "WEB-INF/404.jsp";
        }

        order.setOrderPrice(order.getTicket().getPriceWithDiscount());
        request.getSession().setAttribute("order", order);
        return "redirect:buy-submit";
    }

    private RequestFormMapper<OrderForm> getRequestFormMapper() {
        return request -> new OrderForm(request.getParameter("firstName"),
                request.getParameter("secondName"),
                request.getParameter("ticket"));
    }

    private MapperFormToEntity<Order, OrderForm> getFormEntityMapper(HttpServletRequest request) {
        return form -> Order.builder()
                .firstName(form.getFirstName())
                .secondName(form.getSecondName())
                .cruise((Cruise) request.getSession().getAttribute("cruise"))
                .user((User) request.getSession().getAttribute("user"))
                .excursions(new HashSet<>())
                .build();
    }
}
