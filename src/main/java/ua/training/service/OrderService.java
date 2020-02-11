package ua.training.service;

import ua.training.exception.EmptyOrderListException;
import ua.training.exception.NotEnoughMoney;
import ua.training.dao.DaoFactory;
import ua.training.dao.OrderDao;
import ua.training.exception.SaveOrderException;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;

import java.util.List;

public class OrderService {
    private final OrderDao orderDao;

    public OrderService() {
        this.orderDao = DaoFactory.getInstance().createOrderDao();
    }

    public void buyCruise(User user, Order order) throws SaveOrderException {
        orderDao.buyCruiseChanges(user, order);
    }

    public List<Order> showAllPassengersOnCruise(long id) throws EmptyOrderListException {
        List<Order> orders = orderDao.findAllOrdersByCruise(id);
        if (orders.isEmpty()) {
            throw new EmptyOrderListException("Orders list is empty");
        }
        return orders;
    }

    public List<Order> showAllUserOrders(long id) throws EmptyOrderListException {
        List<Order> orders = orderDao.findAllOrdersByUser(id);
        if (orders.isEmpty()) {
            throw new EmptyOrderListException("Orders list is empty");
        }
        return orders;

    }

    public boolean subUserBalance(User user, long price) {
        long total = user.getBalance() - price;
        if (total < 0) {
            return false;
        }
        user.setBalance(total);
        return true;
    }
}
