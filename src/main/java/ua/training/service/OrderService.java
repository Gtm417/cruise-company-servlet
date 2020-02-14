package ua.training.service;

import ua.training.dao.DaoFactory;
import ua.training.dao.OrderDao;
import ua.training.entity.Order;
import ua.training.entity.User;
import ua.training.exception.EmptyOrderListException;
import ua.training.exception.SaveOrderException;

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

    public List<Order> showAllUserOrders(int page, int size, long id) {
        int offset = page * size - size;
        return orderDao.findAllOrdersByUserWithOffsetAndLimit(offset, size, id);
    }

    public long countAllOrders(long id) {
        return orderDao.countOrdersByUserId(id);
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
