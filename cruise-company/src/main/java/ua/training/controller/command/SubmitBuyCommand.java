package ua.training.controller.command;

import ua.training.controller.command.handler.ExceptionHandler;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.dto.OrderDTO;
import ua.training.model.entity.User;
import ua.training.model.exception.NotEnoughMoney;
import ua.training.model.exception.UnreachableRequest;
import ua.training.model.service.BuyCruiseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SubmitBuyCommand implements Command{
    private final BuyCruiseService buyCruiseService;

    public SubmitBuyCommand() {
        this.buyCruiseService = new BuyCruiseService();
    }
    @Override
    public String execute(HttpServletRequest request) {
        valid(request);
        long resultPrice = Long.parseLong(request.getParameter("resultPrice"));

        OrderDTO orderDTO = (OrderDTO)request.getSession().getAttribute("order");
        orderDTO.setPrice(resultPrice);
        User user = (User) request.getSession().getAttribute("user");
        try {
            user.setBalance(subUserBalance(user, resultPrice));
        } catch (NotEnoughMoney ex) {
            System.err.println("Not Enough Money");
            //todo return to buy.jsp with error
            ExceptionHandler exceptionHandler = new ExceptionHandler(ex, "buy-submit-form");
            return exceptionHandler.handling(request);
        }
        buyCruiseService.buyCruise(user, orderDTO);
        return "redirect:main";
    }
    private boolean valid(HttpServletRequest request){
        if(Objects.isNull(request.getParameter("resultPrice"))
                || Objects.isNull(request.getSession().getAttribute("order"))){
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
