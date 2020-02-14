package ua.training.dao;

import ua.training.entity.Order;
import ua.training.entity.User;
import ua.training.exception.SaveOrderException;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    void buyCruiseChanges(User user, Order order) throws SaveOrderException;

    List<Order> findAllOrdersByCruise(long id);

    List<Order> findAllOrdersByUserWithOffsetAndLimit(int offset, int limit, long id);

    long countOrdersByUserId(long id);
}
