package ua.training.controller.command;

import ua.training.model.dto.OrderDTO;
import ua.training.model.entity.User;
import ua.training.model.exception.NotEnoughMoney;
import ua.training.model.service.BuyCruiseService;

import javax.servlet.http.HttpServletRequest;

public class BuyCruiseCommand implements Command {
    private final BuyCruiseService buyCruiseService;

    public BuyCruiseCommand() {
        this.buyCruiseService = new BuyCruiseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("BuyCruiseCommand");
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String ticket = request.getParameter("ticket");
        String stringCruiseId = request.getParameter("cruiseId");
        String priceParameter = request.getParameter("price");
        if (firstName == null || secondName == null || ticket == null) {
            //todo тут должно быть сообщение на форме что пользователь не всё ввёл
            return "buy.jsp";
        }
        System.out.println("After if");
        long ticketId = Long.parseLong(ticket);
        long cruiseId = Long.parseLong(stringCruiseId);
        long price = Long.parseLong(priceParameter);
        //собрать все данные по ордеру без запроса в бд так как ещё нет экскурсий (ОрдерДТО)
        User user = (User) request.getSession().getAttribute("user");
        try {
            user.setBalance(subUserBalance(user, price));
        } catch (NotEnoughMoney ex) {
            System.err.println("Not Enough Money");
            //todo return to buy.jsp with error
            ex.printStackTrace();
            return "redirect:main";
        }
        //todo сделать через єнтити
        OrderDTO orderDTO = new OrderDTO(cruiseId, user.getId(), ticketId, firstName, secondName);
        orderDTO.setPrice(price);
        request.getSession().setAttribute("order", orderDTO);
        CommandUtility.setSelectedExcursionsListToSession(request);
        //buyCruiseService.buyCruise(user, orderDTO);
        System.out.println("request complete");
        return "redirect:buy-submit-form";
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
