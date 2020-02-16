package ua.training.service;

import ua.training.dao.DaoFactory;
import ua.training.dao.OrderDao;
import ua.training.entity.Order;
import ua.training.entity.User;
import ua.training.exception.SaveOrderException;

import java.util.List;

public class OrderService {
    private final OrderDao orderDao;

    public OrderService() {
        this.orderDao = DaoFactory.getInstance().createOrderDao();
    }

    public void buyCruise(Order order) throws SaveOrderException {
        orderDao.buyCruiseChanges(order);
    }

    public List<Order> showAllPassengersOnCruise(long id) {
        return orderDao.findAllOrdersByCruise(id);
    }

    public List<Order> showAllUserOrders(int page, int size, long id) {
        int offset = page * size - size;
        return orderDao.findAllOrdersByUserWithOffsetAndLimit(offset, size, id);
    }

    public long countAllOrders(long id) {
        return orderDao.countOrdersByUserId(id);
    }

    public long subUserBalance(User user, long price) {
        return user.getBalance() - price;
    }
}
