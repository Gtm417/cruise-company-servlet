package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.exception.TicketNotFound;
import ua.training.model.entity.Cruise;
import ua.training.model.entity.Order;
import ua.training.model.entity.Ticket;
import ua.training.model.entity.User;
import ua.training.service.TicketService;

import javax.servlet.http.HttpServletRequest;

public class BuyCruiseCommand implements Command {
    private final TicketService ticketService;

    public BuyCruiseCommand() {
        this.ticketService = new TicketService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String ticketParam = request.getParameter("ticket");
        String stringCruiseId = request.getParameter("cruiseId");
        String priceParameter = request.getParameter("price");
        if (firstName == null || secondName == null || ticketParam == null) {
            //todo тут должно быть сообщение на форме что пользователь не всё ввёл
            return "redirect:buy-form";
        }
        long ticketId = Long.parseLong(ticketParam);
        long cruiseId = Long.parseLong(stringCruiseId);
        long price = Long.parseLong(priceParameter);
        //собрать все данные по ордеру без запроса в бд так как ещё нет экскурсий (ОрдерДТО)
        User user = (User) request.getSession().getAttribute("user");
        Ticket ticket;
        try {
            ticket = ticketService.findTicketById(ticketId);
        } catch (TicketNotFound e) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(e, "buy-form");
            return exceptionHandler.handling(request);
        }

        Order order = Order.builder()
                .firstName(firstName)
                .secondName(secondName)
                .orderPrice(price)
                .cruise((Cruise) request.getSession().getAttribute("cruise"))
                .user(user)
                .ticket(ticket)
                .build();

        request.getSession().setAttribute("order", order);
        CommandUtility.setSelectedExcursionsListToSession(request);
        return "redirect:buy-submit-form";
    }

}
