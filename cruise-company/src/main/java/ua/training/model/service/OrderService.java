package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.OrderDao;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.exception.EmptyOrderListException;

import java.util.List;

public class OrderService {
    private final OrderDao orderDao;

    public OrderService() {
        this.orderDao = DaoFactory.getInstance().createOrderDao();
    }

    public void buyCruise(User user, Order order){
        orderDao.buyCruiseChanges(user, order);
    }

    public List<Order> showAllPassengersOnCruise(long id) throws EmptyOrderListException {
        List<Order> orders = orderDao.findAllOrdersByCruise(id);
        if(orders.isEmpty()){
            throw new EmptyOrderListException("Orders list is empty");
        }
        return orders;
    }

    public List<Order> showAllUserOrders(long id) throws EmptyOrderListException {
        List<Order> orders = orderDao.findAllOrdersByUser(id);
        if(orders.isEmpty()){
            throw new EmptyOrderListException("Orders list is empty");
        }
        return  orders;

    }
}
