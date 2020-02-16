package ua.training.web.command;

import ua.training.entity.Order;
import ua.training.entity.User;
import ua.training.exception.SaveOrderException;
import ua.training.service.ExcursionService;
import ua.training.service.OrderService;
import ua.training.web.verification.request.Verifier;

import javax.servlet.http.HttpServletRequest;

public class SubmitBuyCommand extends MultipleMethodCommand {
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

        Order order = (Order) request.getSession().getAttribute("order");
        order.setOrderPrice(order.getTicket().getPriceWithDiscount()
                + excursionService.getTotalSumExcursionSet(order.getExcursionList()));

        request.setAttribute("resultPrice", order.getOrderPrice());
        request.setAttribute("excursions",
                excursionService.showAllExcursionsInCruise(order.getCruise()));

        return "buy-submit-form.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {

        verifier.verify(request);
        Order order = (Order) request.getSession().getAttribute("order");
        User user = (User) request.getSession().getAttribute("user");

        if (orderService.subUserBalance(user, order.getOrderPrice()) < 0) {
            return "/WEB-INF/not-enough-money.jsp";
        }

        try {
            orderService.buyCruise(order);
        } catch (SaveOrderException e) {
            //todo log
            return "unsuccess-buy.jsp";
        }
        return "success-buy.jsp";
    }


}
