package ua.training.web.command;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.entity.Order;
import ua.training.entity.User;
import ua.training.exception.SaveOrderException;
import ua.training.service.ExcursionService;
import ua.training.service.OrderService;
import ua.training.web.verification.request.Verifier;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.*;
import static ua.training.web.PageConstants.*;

public class SubmitBuyCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(SubmitBuyCommand.class);

    private final OrderService orderService;
    private final ExcursionService excursionService;
    private final Verifier verifier;

    public SubmitBuyCommand(OrderService orderService, ExcursionService excursionService, Verifier verifier) {
        this.orderService = orderService;
        this.excursionService = excursionService;
        this.verifier = verifier;
    }

    @Override
    protected String performGet(HttpServletRequest request) {

        verifier.verify(request);

        Order order = (Order) request.getSession().getAttribute(SESSION_ORDER_ATTR);
        order.setOrderPrice(order.getTicket().getPriceWithDiscount()
                + excursionService.getTotalSumExcursionSet(order.getExcursionList()));

        request.setAttribute(RESULT_PRICE_REQUEST_ATTR, order.getOrderPrice());
        request.setAttribute(EXCURSIONS_REQUEST_ATTR,
                excursionService.showAllExcursionsInCruise(order.getCruise()));

        return BUY_SUBMIT_FORM_JSP;
    }

    @Override
    protected String performPost(HttpServletRequest request) {

        verifier.verify(request);
        Order order = (Order) request.getSession().getAttribute(SESSION_ORDER_ATTR);
        User user = (User) request.getSession().getAttribute(SESSION_USER_ATTR);

        if (orderService.subUserBalance(user, order.getOrderPrice()) < 0) {
            return NOT_ENOUGH_MONEY_JSP;
        }

        try {
            orderService.buyCruise(order);
        } catch (SaveOrderException e) {
            LOGGER.info(e.getMessage());
            return UNSUCCESS_BUY_JSP;
        }
        return SUCCESS_BUY_JSP;
    }


}
