package ua.training.web.command;

import ua.training.entity.User;
import ua.training.service.OrderService;
import ua.training.web.PageConstants;

import javax.servlet.http.HttpServletRequest;

import static ua.training.web.AttributeConstants.ORDERS_REQUEST_ATTR;
import static ua.training.web.AttributeConstants.SESSION_USER_ATTR;
import static ua.training.web.CommandConstants.ALL_ORDERS_COMMAND;
import static ua.training.web.PageConstants.PAGE_404_JSP;

public class AllOrdersCommand implements Command {
    private final OrderService orderService;
    private final Pagination pagination;

    public AllOrdersCommand(OrderService orderService, Pagination pagination) {
        this.orderService = orderService;
        this.pagination = pagination;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String sizeParam = request.getParameter("size");
        String pageParam = request.getParameter("page");
        if (!pagination.validatePaginationParams(sizeParam, pageParam)) {
            return PAGE_404_JSP;
        }

        int page = Integer.parseInt(pageParam);
        int size = Integer.parseInt(sizeParam);
        long userId = ((User) request.getSession().getAttribute(SESSION_USER_ATTR)).getId();

        pagination.paginate(page, size, orderService.countAllOrders(userId), request, ALL_ORDERS_COMMAND);

        request.setAttribute(ORDERS_REQUEST_ATTR, orderService.showAllUserOrders(page, size, userId));

        return PageConstants.ALL_ORDERS_JSP;
    }

}
