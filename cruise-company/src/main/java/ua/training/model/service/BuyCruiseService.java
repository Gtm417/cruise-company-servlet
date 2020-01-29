package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.OrderDao;
import ua.training.model.dto.OrderDTO;
import ua.training.model.entity.User;

public class BuyCruiseService {
    private final OrderDao orderDao;

    public BuyCruiseService() {
        this.orderDao = DaoFactory.getInstance().createOrderDao();
    }

    public void buyCruise(User user, OrderDTO orderDTO){
        orderDao.buyCruiseChanges(user, orderDTO);
    }
}
