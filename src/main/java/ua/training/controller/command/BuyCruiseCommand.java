package ua.training.controller.command;

import ua.training.controller.form.OrderForm;
import ua.training.controller.form.validation.Validator;
import ua.training.controller.handler.ExceptionHandler;
import ua.training.controller.mapper.MapperFormToEntity;
import ua.training.controller.mapper.RequestFormMapper;
import ua.training.exception.TicketNotFound;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;

public class BuyCruiseCommand implements Command {
    private final TicketService ticketService;
    private final Validator<OrderForm> validator;

    public BuyCruiseCommand(TicketService ticketService, Validator<OrderForm> validator) {
        this.ticketService = ticketService;
        this.validator = validator;
    }

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.checkCruiseInSession(request);
        OrderForm orderForm = getRequestFormMapper().mapToForm(request);

        if (validator.validate(orderForm)) {
            request.setAttribute("errors", true);
            return "redirect:buy-form";
        }

        Order order = getFormEntityMapper(request).mapToEntity(orderForm);
        try {
            order.setTicket(ticketService.findTicketById(Long.parseLong(orderForm.getTicket())));
        } catch (TicketNotFound e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "buy-form");
            return exceptionHandler.handling(request);
        }
        order.setOrderPrice(order.getTicket().getPriceWithDiscount());
        request.getSession().setAttribute("order", order);
        CommandUtility.setSelectedExcursionsListToSession(request);
        return "redirect:buy-submit-form";
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
                .build();
    }


}
