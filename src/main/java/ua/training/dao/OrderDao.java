package ua.training.dao;

import ua.training.entity.Order;
import ua.training.exception.SaveOrderException;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    void buyInTransaction(Order order) throws SaveOrderException;

    List<Order> findAllOrdersByCruise(long id);

    List<Order> findAllOrdersByUserWithOffsetAndLimit(int offset, int limit, long id);

    int countOrdersByUserId(long id);
}
