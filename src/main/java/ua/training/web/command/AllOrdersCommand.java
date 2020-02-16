package ua.training.web.command;

import ua.training.entity.User;
import ua.training.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class AllOrdersCommand extends MultipleMethodCommand implements Pagination {
    private final OrderService orderService;

    public AllOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        String sizeParam = request.getParameter("size");
        String pageParam = request.getParameter("page");
        if (!validatePaginationParams(sizeParam, pageParam)) {
            return "WEB-INF/404.jsp";
        }

        int page = Integer.parseInt(pageParam);
        int size = Integer.parseInt(sizeParam);
        long userId = ((User) request.getSession().getAttribute("user")).getId();

        paginate(page, size, orderService.countAllOrders(userId), request, "all-orders");

        request.setAttribute("orders", orderService.showAllUserOrders(page, size, userId));

        return "all-orders.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        return null;
    }
}
