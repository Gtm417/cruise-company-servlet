package ua.training.model.dao;

import ua.training.model.dto.OrderDTO;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;

public interface OrderDao extends GenericDao<Order>  {
    void buyCruiseChanges(User user, OrderDTO orderDTO);
}
