package ua.training.web.command;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

import static ua.training.web.AttributeConstants.*;
import static ua.training.web.CommandConstants.BUY_SUBMIT_COMMAND;
import static ua.training.web.CommandConstants.REDIRECT_COMMAND;
import static ua.training.web.PageConstants.BUY_JSP;
import static ua.training.web.PageConstants.PAGE_404_JSP;

public class BuyCruiseCommand extends MultipleMethodCommand {

    private static Logger LOGGER = LogManager.getLogger(BuyCruiseCommand.class);

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

        if (request.getSession().getAttribute(SESSION_CRUISE_ATTR) != null) {
            Cruise cruise = (Cruise) request.getSession().getAttribute(SESSION_CRUISE_ATTR);
            cruise.setTickets(ticketService.findAllTicketsForBuy(cruise.getId()));
            return BUY_JSP;
        }

        long cruiseId = Long.parseLong(request.getParameter("cruiseId"));

        Cruise cruise;
        try {
            cruise = cruiseService.findById(cruiseId);
        } catch (CruiseNotFoundException e) {
            LOGGER.info(e.getMessage());
            return PAGE_404_JSP;
        }

        if (!cruiseService.checkAmountPassenger(cruise)) {
            request.setAttribute(NO_PLACES_ATTR, true);
        }

        cruise.setTickets(ticketService.findAllTicketsForBuy(cruiseId));
        request.getSession().setAttribute(SESSION_CRUISE_ATTR, cruise);
        return BUY_JSP;
    }

    @Override
    protected String performPost(HttpServletRequest request) {

        CommandUtility.checkCruiseInSession(request);
        OrderForm orderForm = getRequestFormMapper().mapToForm(request);
        if (validator.validate(orderForm)) {
            request.setAttribute(ERRORS_REQUEST_ATTR, true);
            return BUY_JSP;
        }

        Order order = getFormEntityMapper(request).mapToEntity(orderForm);

        try {
            order.setTicket(ticketService.findTicketByIdAndCruise(Long.parseLong(orderForm.getTicket()), order.getCruise().getId()));
        } catch (TicketNotFound e) {
            LOGGER.info(e.getMessage());
            return PAGE_404_JSP;
        }

        order.setOrderPrice(order.getTicket().getPriceWithDiscount());
        request.getSession().setAttribute(SESSION_ORDER_ATTR, order);
        return REDIRECT_COMMAND + BUY_SUBMIT_COMMAND;
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
                .cruise((Cruise) request.getSession().getAttribute(SESSION_CRUISE_ATTR))
                .user((User) request.getSession().getAttribute(SESSION_USER_ATTR))
                .excursions(new HashSet<>())
                .build();
    }
}
