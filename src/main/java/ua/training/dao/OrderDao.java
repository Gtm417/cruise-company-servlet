package ua.training.dao;

import ua.training.exception.SaveOrderException;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    void buyCruiseChanges(User user, Order order) throws SaveOrderException;

    List<Order> findAllOrdersByCruise(long id);

    List<Order> findAllOrdersByUser(long id);
}
