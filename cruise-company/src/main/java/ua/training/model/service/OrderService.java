package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.OrderDao;
import ua.training.model.dto.OrderDTO;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.exception.EmptyPassengerList;

import java.util.List;

public class OrderService {
    private final OrderDao orderDao;

    public OrderService() {
        this.orderDao = DaoFactory.getInstance().createOrderDao();
    }

    public void buyCruise(User user, OrderDTO orderDTO){
        orderDao.buyCruiseChanges(user, orderDTO);
    }

    public List<Order> showAllPassengersOnCruise(long id) throws EmptyPassengerList {
        List<Order> orders = orderDao.findAllOrdersByCruise(id);
        if(orders.isEmpty()){
            throw new EmptyPassengerList("Orders list is empty");
        }
        return orders;
    }
}
