package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.exception.NotEnoughMoney;
import ua.training.exception.UnreachableRequest;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SubmitBuyCommand implements Command {
    private final OrderService orderService;

    public SubmitBuyCommand() {
        this.orderService = new OrderService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        valid(request);
        long resultPrice = Long.parseLong(request.getParameter("resultPrice"));
        Order order = (Order) request.getSession().getAttribute("order");
        order.setOrderPrice(resultPrice);
        User user = (User) request.getSession().getAttribute("user");
        try {
            user.setBalance(subUserBalance(user, resultPrice));
        } catch (NotEnoughMoney ex) {
            System.err.println("Not Enough Money");
            ExceptionHandler exceptionHandler = new ExceptionHandler(ex, "buy-submit-form");
            return exceptionHandler.handling(request);
        }
        orderService.buyCruise(user, order);
        return "redirect:main";
    }

    private boolean valid(HttpServletRequest request) {
        if (Objects.isNull(request.getParameter("resultPrice"))
                || Objects.isNull(request.getSession().getAttribute("order"))) {
            throw new UnreachableRequest();
        }
        return true;
    }

    private long subUserBalance(User user, long price) throws NotEnoughMoney {
        long total = user.getBalance() - price;
        System.out.println(total);
        if (total < 0) {
            throw new NotEnoughMoney("Not enough money ", user.getBalance());
        }
        return total;
    }
}
